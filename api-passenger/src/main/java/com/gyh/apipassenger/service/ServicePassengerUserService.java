package com.gyh.apipassenger.service;

import com.gyh.internalcommon.dto.ResponseResult;

public interface ServicePassengerUserService {

    ResponseResult login(String passengerPhone);
}
