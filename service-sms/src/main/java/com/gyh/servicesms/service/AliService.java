package com.gyh.servicesms.service;

import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;

public interface AliService {
    /**
     * 发送短信
     * @param request
     * @return
     * */
    int sendSms(SmsSendRequest request) throws Exception;
}
