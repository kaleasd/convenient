package com.gyh.mapservice.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.mapservice.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/10/26
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping("/sid")
    public ResponseResult getConfig(){

        return configService.getSid();
    }
}
