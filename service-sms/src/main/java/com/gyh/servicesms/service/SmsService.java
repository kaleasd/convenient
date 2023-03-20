package com.gyh.servicesms.service;


import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;

public interface SmsService {
	/**
	 * 发送短信
	 * @param request
	 * @return
	 */
	public ResponseResult sendSms(SmsSendRequest request);
}