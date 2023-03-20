package com.gyh.apipassenger.feign;

import com.gyh.apipassenger.feign.request.CodeVerifyRequest;
import com.gyh.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SERVICE-VERIFICATION-CODE")
public interface ServiceVerificationCodeFeignClient {

    @RequestMapping(value = "/verify-code/verify",method = RequestMethod.POST)
    public ResponseResult verify(@RequestBody CodeVerifyRequest codeVerifyRequest);
}
