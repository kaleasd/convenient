package com.gyh.apidriver.service;

import com.gyh.internalcommon.dto.order.ForecastRequest;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;

import java.util.List;

public interface OrderService {
    /**
     * 根据起止经纬度计算预估价格
     * @param forecastRequest
     * @return
     */
    public Double forecast(ForecastRequest forecastRequest) ;


}
