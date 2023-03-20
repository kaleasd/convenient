package com.gyh.apidriver.service;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;

public interface RestTemplateRequestService {
	
	ResponseResult smsSend(SmsSendRequest smsSendRequest);
	
	String grabOrder(int orderId, int driverId);

}
