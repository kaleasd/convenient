package com.gyh.servicesms.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gyh
 * */
@Slf4j
@RestController
@RequestMapping("/service-sms-gateway-test")
public class GatewayTestController {
    @RequestMapping(value = "/api1", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult send(HttpServletRequest request) {
        return ResponseResult.success("");
    }

}
