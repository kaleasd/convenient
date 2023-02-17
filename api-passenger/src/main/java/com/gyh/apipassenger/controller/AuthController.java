package com.gyh.apipassenger.controller;

import com.gyh.apipassenger.request.UserAuthRequest;
import com.gyh.apipassenger.service.AuthService;
import com.gyh.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestBody @Validated UserAuthRequest userAuthRequest) {
        String passengerPhone = userAuthRequest.getPassengerPhone();
        String code = userAuthRequest.getCode();
        return authService.auth(passengerPhone, code);
    }
}
