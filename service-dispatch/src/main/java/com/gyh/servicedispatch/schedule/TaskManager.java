package com.gyh.servicedispatch.schedule;

import com.gyh.internalcommon.constant.BusinessInterfaceStatus;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.entity.Order;
import com.gyh.internalcommon.entity.OrderRulePrice;
import com.gyh.servicedispatch.consts.Const;
import com.gyh.servicedispatch.consts.OrderTypeEnum;
import com.gyh.servicedispatch.context.TaskStore;
import com.gyh.servicedispatch.service.ConfigService;
import com.gyh.servicedispatch.service.DispatchService;
import com.gyh.servicedispatch.task.ITask;
import com.gyh.servicedispatch.task.OrderTaskFactory;
import com.gyh.servicedispatch.task.TaskCondition;
import com.gyh.servicedispatch.task.impl.OrderForceTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TaskManager {
    private static final String ORDER_ID_IS_NULL = "订单ID为空";
    private static final String ORDER_STATER_TIME_IS_NULL = "订单开始时间为空";
    private static final String TASK_CONDITIONS_IS_NULL = "任务为空";

    @Autowired
    private TaskStore taskStore;
    @Autowired
    private DispatchService dispatchService;
    @Autowired
    private ConfigService configService;

    /**
     * 派单
     *
     * @param orderId
     * @return
     */
    @Async
    public ResponseResult dispatch(int orderId) {
        // 派单任务， 定时执行
        Order order = dispatchService.getOrderById(orderId);
        if (taskStore.getResults().containsKey(orderId)) {
            log.info("#orderId = " + orderId + " 重复派单");
            return null;
        }
        if (StringUtils.isEmpty(order.getDeviceCode())) {
            log.info("#orderId = " + orderId + "  device code null");
            return null;
        }
        //serviceTypeId,cityCode
        OrderRulePrice orderRulePrice = dispatchService.getOrderRulePrice(orderId);
        int type = -1;
        if (null == order.getStartTime()) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), ORDER_STATER_TIME_IS_NULL);
        }
        List<TaskCondition> taskConditions = null;
        ITask task = null;
        boolean isOpenForce = configService.isOpenForceSendOrder(orderRulePrice.getCityCode());
        if (order.getStatus() == Const.ORDER_STATUS_ORDER_START && order.getIsFakeSuccess() == 0) {
            // 小于30分钟，强派
            int forceTime = configService.getForceSendOrderTime(orderRulePrice.getCityCode(), Const.TIME_THRESHOLD_TYPE_FORCE);
            log.info("#orderId = " + orderId + " 强派时间设置 = " + forceTime);
            if (order.getOrderStartTime().getTime() - System.currentTimeMillis() < TimeUnit.MINUTES.toMillis(forceTime)) {
                if (order.getServiceType() != OrderTypeEnum.REAL_TIME.getCode()) {
                    if (!isOpenForce) {
                        log.info("#orderId = " + orderId + " 强派没开启");
                        return null;
                    }
                }
                type = OrderTypeEnum.FORCE.getCode();
                log.info("dispatch------强派");
                //轮数
                int round = 1;
                if (order.getIsFakeSuccess() == 1) {
                    log.info("dispatch------加成功强派");
                    round = 3;
                }
                //派单规则，一轮一个task，多轮
                taskConditions = dispatchService.getForceTaskCondition(orderRulePrice.getCityCode(), order.getServiceType(), round);
                task = new OrderForceTask(orderId, type);
            } else {
                //特殊时段
                boolean isSpecial = dispatchService.isSpecial(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), order.getOrderStartTime().getTime());
                if (isSpecial) {
                    type = OrderTypeEnum.SPECIAL.getCode();
                    log.info("dispatch------预约单，特殊时段");
                    taskConditions = dispatchService.getSpecialCondition(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId());
                } else {
                    type = OrderTypeEnum.NORMAL.getCode();
                    log.info("dispatch------预约单，普通时段");
                    taskConditions = dispatchService.getNormalCondition(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId());
                }
                task = OrderTaskFactory.createTask(orderId, order.getServiceType(), type);
            }
        }
        if (order.getStatus() == Const.ORDER_STATUS_ORDER_START && order.getIsFakeSuccess() == 1) {
            if (!isOpenForce) {
                log.info("#orderId = " + orderId + " 强派没开启");
                return null;
            }
            if (order.getOrderStartTime().getTime() - System.currentTimeMillis() < TimeUnit.MINUTES.toMillis(configService.getForceSendOrderTime(orderRulePrice.getCityCode(), order.getServiceType()))) {
                type = OrderTypeEnum.FORCE.getCode();
                log.info("dispatch------强派");
                //轮数
                int round = 1;
                if (order.getIsFakeSuccess() == 1) {
                    log.info("dispatch------假成功强派");
                    round = 3;
                }
                //派单规则，一轮一个task，多轮
                task = new OrderForceTask(orderId, type);
                taskConditions = dispatchService.getForceTaskCondition(orderRulePrice.getCityCode(), order.getServiceType(), round);
            }
        }

        if (taskConditions == null) {
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), TASK_CONDITIONS_IS_NULL);
        }
        log.info("#orderId = " + orderId + " type = " + type);
        task.setTaskConditions(taskConditions);
        int status = task.execute(System.currentTimeMillis());
        if (status != -1) {
            taskStore.addTask(task.getTaskId(), task);
        }
        return ResponseResult.success("派单成功");
    }

    @Async
    public void retry(ITask task) {
        int status = task.execute(System.currentTimeMillis());
        if (status != -1) {
            taskStore.addTask(task.getTaskId(), task);
        }
    }
}
