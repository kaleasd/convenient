package com.gyh.apipassenger.service;

import com.gyh.internalcommon.dto.ResponseResult;

public interface ServiceVerificationCodeRestTemplateService {

    ResponseResult generatorCode(int identity, String phoneNumber);

    ResponseResult verifyCode(int identity, String phoneNumber, String code);
}
