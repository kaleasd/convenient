package com.gyh.apipassenger.service.impl;

import com.gyh.apipassenger.service.AuthService;
import com.gyh.apipassenger.service.ServicePassengerUserService;
import com.gyh.apipassenger.service.ServiceVerificationCodeRestTemplateService;
import com.gyh.internalcommon.constant.CommonStatusEnum;
import com.gyh.internalcommon.constant.IdentityConstant;
import com.gyh.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ServiceVerificationCodeRestTemplateService templateService;
    @Autowired
    private ServicePassengerUserService userService;
    @Override
    public ResponseResult auth(String passengerPhone, String code) {
        // 验证验证码；
        ResponseResult responseResult = templateService.verifyCode(IdentityConstant.PASSENGER, passengerPhone, code);
        if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("登陆失败：验证码校验失败");
        }
        // 用户登录
        responseResult = userService.login(passengerPhone);
        if (responseResult.getCode() != CommonStatusEnum.SUCCESS.getCode()) {
            return ResponseResult.fail("登陆失败");
        }
        return responseResult;
    }
}
