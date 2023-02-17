package com.gyh.serviceverificationcode.service;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;

public interface VerifyCodeService {

    /**
     * 根据身份和手机号生成验证码
     * @param identity
     * @param phoneNumber
     * @return
     * */
    ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber);

    /**
     * 校验身份，手机号，验证码的合法性
     * @param identity
     * @param phoneNumber
     * @param code
     * @return
     * */
    ResponseResult verify(int identity, String phoneNumber, String code);
}
