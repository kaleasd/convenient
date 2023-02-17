package com.gyh.apipassenger.service;

import com.gyh.internalcommon.dto.ResponseResult;

public interface VerificationCodeService {

    ResponseResult send(String phoneNumber);

    /**
     * 校验身份，手机号，验证码的合法性
     * @param identity
     * @param phoneNumber
     * @param code
     * @return
     */
    public ResponseResult verify(int identity,String phoneNumber,String code);
}
