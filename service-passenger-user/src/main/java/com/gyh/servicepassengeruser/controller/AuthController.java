package com.gyh.servicepassengeruser.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicepassengeruser.request.LoginRequest;
import com.gyh.servicepassengeruser.service.PassengerUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PassengerUserService passengerUserService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@RequestBody LoginRequest request) {
        String passengerPhone = request.getPassengerPhone();
        return ResponseResult.success(passengerUserService.login(passengerPhone));
    }

    /**
     * @param token
     * @return
     * @throws Exception
     * */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult logout(String token) {
        return ResponseResult.success(passengerUserService.logout(token));
    }
}
