package com.gyh.servicedispatch.service;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.request.OrderRequest;
import com.gyh.internalcommon.dto.phone.BoundPhoneDto;
import com.gyh.internalcommon.dto.push.PushLoopBatchRequest;
import com.gyh.internalcommon.dto.push.PushRequest;
import com.gyh.internalcommon.entity.*;
import com.gyh.servicedispatch.data.DriverData;
import com.gyh.servicedispatch.request.DispatchRequest;
import com.gyh.servicedispatch.task.TaskCondition;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface DispatchService {
    String test2();

    PassengerInfo getPassengerInfo(int id);

    String getChargeRuleStr(ChargeRule chargeRule);

    ChargeRule getChargeRule(String cityCode, int serviceTypeId, int carLevelId);

    TagInfo getTagInfo(int id);

    String getOssFileUrl();

    double getDriverEvaluateByDriverId(int driverId);

    int pushMessage(PushRequest pushRequest);

    void updateAmapOrder(OrderRequest orderRequest);

    boolean isSpecial(String cityCode, int serviceTypeId, long time);

    void loopMessageBatch(PushLoopBatchRequest pushLoopBatchRequest);

    BoundPhoneDto bindAxb(String phone1, String phone2, long expireTime);

    void unbind(String subId, String secretNo);

    double calDistance(String long1, String lat1, String long2, String lat2);

    boolean updateOrder(Order order);

    List<TaskCondition> getForceTaskCondition(String cityCode, int serviceTypeId, int round);

    List<TaskCondition> getSpecialCondition(String cityCode, int serviceTypeId);

    List<TaskCondition> getNormalCondition(String cityCode, int serviceTypeId);

    void sendSmsMessage(String phone, String code, Map<String, Object> map);

    void sendSmsMessageHx(String phone, String code, String... content);

    int countDriverOrder(int id, Date startTime, Date endTime);

    ResponseResult dispatch(DispatchRequest dispatchRequest);

    List<DriverData> getCarByOrder(Order order, TaskCondition taskCondition, int distance, List<Integer> usedIds, int round, boolean searchType);

    boolean hasDriver(String city, Date time, int carType, int serviceTypeId);

    boolean hasDriver2(String city, Date time, int carType, int serviceTypeId);

    int getDateTimeType(Date date);

    void test(int id);

    Order getOrderById(int id);

    OrderRulePrice getOrderRulePrice(int orderId);

    void updateDriverInfo(DriverInfo updateDriverInfo);

    CarInfo getCarInfoById(int id);


}
