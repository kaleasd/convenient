package com.gyh.servicedispatch.service;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.Dispatch;
import com.gyh.internalcommon.dto.map.request.OrderRequest;
import com.gyh.internalcommon.dto.phone.BoundPhoneDto;
import com.gyh.internalcommon.dto.push.PushLoopBatchRequest;
import com.gyh.internalcommon.dto.push.PushRequest;
import com.gyh.internalcommon.entity.Order;
import com.gyh.servicedispatch.request.DispatchRequest;
import com.gyh.servicedispatch.request.DistanceRequest;

import java.util.Map;

public interface HttpService {

    ResponseResult sendSms(String phone, String smsCode, Map<String, Object> templateMap);

    ResponseResult sendSms(String phone, String smsCode, String... content);

    ResponseResult updateAmapOrder(OrderRequest orderRequest);

    ResponseResult<Dispatch> dispatch(DispatchRequest dispatchRequest);

    double calDistance(DistanceRequest distanceRequest);

    void unbind(String subId, String secretNo);

    BoundPhoneDto bind(String phone1, String phone2, long expireTime);

    boolean updateOrder(Order order);

    ResponseResult pushMsg(PushRequest pushRequest);

    ResponseResult loopMessage(PushLoopBatchRequest request);

    ResponseResult loopMessageBatch(PushLoopBatchRequest request);
}
