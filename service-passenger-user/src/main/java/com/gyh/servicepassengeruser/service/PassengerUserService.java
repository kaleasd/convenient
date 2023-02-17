package com.gyh.servicepassengeruser.service;

import com.gyh.internalcommon.dto.ResponseResult;

public interface PassengerUserService {
    ResponseResult login(String passengerPhone);

    ResponseResult logout(String token);
}
