package com.gyh.apipassenger.feign;

import com.gyh.apipassenger.feign.response.PassengerUserInfo;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-passenger-user")
public interface ServicePassengerUserFeignClient {

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    ResponseResult<PassengerUserInfo> login(@RequestBody LoginRequest loginRequest);
}
