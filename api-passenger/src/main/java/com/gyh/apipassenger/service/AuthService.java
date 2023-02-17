package com.gyh.apipassenger.service;

import com.gyh.internalcommon.dto.ResponseResult;

public interface AuthService {

    ResponseResult auth(String passengerPhone, String code);
}
