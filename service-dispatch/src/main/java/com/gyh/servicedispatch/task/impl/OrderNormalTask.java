package com.gyh.servicedispatch.task.impl;

import com.gyh.internalcommon.constant.IdentityEnum;
import com.gyh.internalcommon.dto.push.PushLoopBatchRequest;
import com.gyh.internalcommon.dto.push.PushRequest;
import com.gyh.internalcommon.entity.ChargeRule;
import com.gyh.internalcommon.entity.Order;
import com.gyh.internalcommon.entity.OrderRulePrice;
import com.gyh.internalcommon.util.EncriptUtil;
import com.gyh.servicedispatch.consts.Const;
import com.gyh.servicedispatch.consts.MessageType;
import com.gyh.servicedispatch.consts.OrderTypeEnum;
import com.gyh.servicedispatch.data.DriverData;
import com.gyh.servicedispatch.data.OrderDto;
import com.gyh.servicedispatch.service.DispatchService;
import com.gyh.servicedispatch.service.impl.DispatchServiceImpl;
import com.gyh.servicedispatch.task.TaskCondition;
import com.gyh.servicedispatch.util.DateUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class OrderNormalTask extends AbstractTask{

    public OrderNormalTask(int orderId, int type) {
        this.orderId = orderId;
        this.type = type;
    }
    @Override
    public boolean sendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round) {
        List<DriverData> list = DispatchServiceImpl.ins().getCarByOrder(order, taskCondition, taskCondition.getDistance(), usedDriverId, round, true);
        if (list == null) {
            status = STATUS_END;
            return false;
        }
        if (list.size() == 0) {
            log.info("#orderId= " + orderId + "  round = " + round + "司机数量0，直接下一轮");
            return false;
        }
        //推送
        JSONObject messageBody = new JSONObject();
        messageBody.put("orderId", orderId);
        messageBody.put("startTime", order.getOrderStartTime().getTime());
        messageBody.put("startAddress", order.getStartAddress());
        messageBody.put("endAddress", order.getEndAddress());
        messageBody.put("forecastPrice", orderRulePrice.getTotalPrice());
        messageBody.put("forecastDistance", orderRulePrice.getTotalDistance());
        messageBody.put("serviceType", order.getServiceType());
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
            messageBody.put("forecastTime", 8);
        }
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode()) {
            messageBody.put("forecastTime", 4);
        }
        messageBody.put("tagList", getTagsJson(order.getUserFeature()));
        String timeDesc = DateUtils.formatDate(order.getOrderStartTime(), DateUtils.yyMMddHHmm);
        String passengerPhone = EncriptUtil.decryptionPhoneNumber(order.getOtherPhone() == null || order.getOtherPhone().isEmpty() ? order.getPassengerPhone() : order.getOtherPhone());
        String content = "";
        if (order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_HALF.getCode() || order.getServiceType() == OrderTypeEnum.CHARTERED_CAR_FULL.getCode()) {
            content = " 您收到一条" + getTypeDesc2(order) + "预约单是否抢单，" + timeDesc + "上车点" + order.getStartAddress();
        } else {
            content = "您收到一条" + getTypeDesc2(order) + "预约单是否抢单," + timeDesc + "乘客尾号" + StringUtils.substring(passengerPhone, passengerPhone.length() - 4) + ",从" + order.getStartAddress() + "到" + order.getEndAddress() + "的订单";
        }
        messageBody.put("content", content);
        messageBody.put("userFeature", order.getUserFeature());
        ChargeRule chargeRule = DispatchServiceImpl.ins().getChargeRule(orderRulePrice.getCityCode(), orderRulePrice.getServiceTypeId(), orderRulePrice.getCarLevelId());
        messageBody.put("charterCarInfo", DispatchServiceImpl.ins().getChargeRuleStr(chargeRule));
        List<String> driverList = new ArrayList<>();
        List<String> carScreenList = new ArrayList<>();
        int count = 0;
        for (DriverData data : list) {
            log.info("#orderId= " + orderId + "  round = " + round + "司机信息：" + JSONObject.fromObject(data));
            usedDriverId.add(data.getDriverInfo().getId());
            driverList.add(data.getDriverInfo().getId() + "");
            carScreenList.add(data.getCarInfo().getLargeScreenDeviceCode());
            count++;
            if (count >= taskCondition.getDriverNum()) {
                break;
            }
        }
        Order newOrder = DispatchServiceImpl.ins().getOrderById(orderId);
        if (newOrder != null) {
            if (newOrder.getStatus() != Const.ORDER_STATUS_ORDER_START) {
                return false;
            }
        }
        if (driverList.size() > 0) {
            PushLoopBatchRequest request1 = new PushLoopBatchRequest(IdentityEnum.DRIVER.getCode(), driverList, MessageType.DRIVER_RESERVED, messageBody.toString(), order.getPassengerInfoId() + "", Const.IDENTITY_PASSENGER);
            log.info("#orderId= " + orderId + "  round = " + round + "  sendOrder PushLoopBatchRequest = " + request1);
            DispatchServiceImpl.ins().loopMessageBatch(request1);
        }
        if (carScreenList.size() > 0) {
            PushLoopBatchRequest request2 = new PushLoopBatchRequest(IdentityEnum.CAR_SCREEN.getCode(), carScreenList, MessageType.CAR_SCREEN_RESERVED, messageBody.toString(), order.getPassengerInfoId() + "", Const.IDENTITY_PASSENGER);
            DispatchServiceImpl.ins().loopMessageBatch(request2);
        }
        return true;
    }

    @Override
    public void taskEnd(Order order, OrderRulePrice orderRulePrice) {
        if (type == OrderTypeEnum.NORMAL.getCode()) {
            if (DispatchServiceImpl.ins().hasDriver2(orderRulePrice.getCityCode(), order.getOrderStartTime(), orderRulePrice.getCarLevelId(), order.getServiceType())) {
                OrderDto updateOrder = new OrderDto();
                updateOrder.setOrderId(order.getId());
                updateOrder.setId(order.getId());
                updateOrder.setIsFakeSuccess(1);
                DispatchServiceImpl.ins().updateOrder(updateOrder);
                log.info("#orderId= " + "  round = " + round + orderId + "  假成功");

                PushRequest pushRequest = new PushRequest();
                pushRequest.setSendId(order.getPassengerInfoId() + "");
                pushRequest.setSendIdentity(IdentityEnum.PASSENGER.getCode());
                pushRequest.setAcceptIdentity(IdentityEnum.PASSENGER.getCode());
                pushRequest.setAcceptId(order.getPassengerInfoId() + "");
                pushRequest.setMessageType(MessageType.FAKE_SCUCCESS);
                pushRequest.setTitle("假成功");
                JSONObject msg = new JSONObject();
                msg.put("messageType", MessageType.FAKE_SCUCCESS);
                msg.put("orderId", order.getId());
                pushRequest.setMessageBody(msg.toString());
                pushRequest.setBusinessMessage(msg.toString());
                log.info("#orderId= " + orderId + "  round = " + round + "  假成功消息 pushRequest = " + pushRequest);
                DispatchServiceImpl.ins().pushMessage(pushRequest);
            }
        }
        log.info("#orderId= " + orderId + "  OrderNormalTask 派单结束");
    }
}
