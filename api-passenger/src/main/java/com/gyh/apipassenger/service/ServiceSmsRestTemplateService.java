package com.gyh.apipassenger.service;

import com.gyh.internalcommon.dto.ResponseResult;

public interface ServiceSmsRestTemplateService {

    ResponseResult sendSms(String phoneNumber, String code);
}
