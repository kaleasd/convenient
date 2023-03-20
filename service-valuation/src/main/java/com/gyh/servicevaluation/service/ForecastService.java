package com.gyh.servicevaluation.service;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.order.ForecastRequest;
import com.gyh.internalcommon.dto.order.ForecastResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-valuation")
public interface ForecastService {

    @RequestMapping(value = "/forecast/single", method = RequestMethod.POST)
    public ResponseResult<ForecastResponse> forecast(@RequestBody ForecastRequest forecastRequest);
}
