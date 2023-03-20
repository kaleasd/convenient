package com.gyh.servicevaluation.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.order.ForecastRequest;
import com.gyh.internalcommon.dto.order.ForecastResponse;
import com.gyh.servicevaluation.service.ForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/val")
public class ForecastController {

    @Autowired
    private ForecastService forecastService;

    @RequestMapping(value = "/forecast", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<ForecastResponse> forecast(@RequestBody ForecastRequest forecastRequest) {
        ResponseResult<ForecastResponse> result = forecastService.forecast(forecastRequest);
        return ResponseResult.success(result.getData());
    }
}
