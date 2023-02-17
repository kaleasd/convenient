package com.gyh.mapservice.service.impl;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.mapservice.response.ConfigResponse;
import com.gyh.mapservice.service.ConfigService;
import org.springframework.beans.factory.annotation.Value;

public class ConfigServiceImpl implements ConfigService {
    @Value("${amap.sid}")
    private String amapSid;
    @Override
    public ResponseResult getSid() {
        ConfigResponse configResponse = new ConfigResponse();
        configResponse.setKey("");
        configResponse.setSid(amapSid);
        return ResponseResult.success(configResponse);
    }
}
