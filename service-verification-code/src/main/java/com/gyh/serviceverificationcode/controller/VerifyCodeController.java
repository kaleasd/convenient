package com.gyh.serviceverificationcode.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;
import com.gyh.serviceverificationcode.request.CodeVerifyRequest;
import com.gyh.serviceverificationcode.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify-code")
@Slf4j
public class VerifyCodeController {

    @Autowired
    private VerifyCodeService verifyCodeService;

    /**
     * 根据身份，手机号，生成验证码
     * @param identity
     * @param phoneNumber
     * */
    @RequestMapping(value = "/generate/{identity}/{phoneNumber}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult<VerifyCodeResponse> generate(@PathVariable("identity") int identity, @PathVariable("phoneNumber")String phoneNumber) {
        log.info("身份类型：" + identity + ", 手机号：" + phoneNumber);
        // 校验参数
        return verifyCodeService.generate(identity, phoneNumber);
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult verify(@RequestBody CodeVerifyRequest request) {
        log.info("/verify-code/verify   request:" + JSONObject.getNames(request));
        // 获取手机号和验证码
        return verifyCodeService.verify(request.getIdentity(), request.getPhoneNumber(), request.getCode());
    }
}
