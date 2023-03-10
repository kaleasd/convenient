package com.gyh.servicevaluation.service;

import com.gyh.servicevaluation.dto.ForecastDetail;
import com.gyh.servicevaluation.dto.request.CurrentPriceRequestDto;
import com.gyh.servicevaluation.dto.response.CurrentPriceResponseDto;

import java.math.BigDecimal;

/**
 * 计价服务
 *
 * @date 2023/2/16
 * */
public interface ValuationService {

    String ERR_DISCOUNT_RATE_RANGE = "动态调价的折扣取值范围不在0-1内";

    String ERR_EXPIRE_FORECAST = "预估超过有效期限";

    /**
     * 计算预估价格
     *
     * @param orderId 订单ID
     * @return 预估价格
     * */
    BigDecimal calcForecastPrice(Integer orderId);

    /**
     * 结束预估，写入数据库
     *
     * @param orderId 订单ID
     * */
    void doneForecast(Integer orderId);

    /**
     * 请求预估明细
     *
     * @param orderId 订单ID
     * @return 预估明细DTO
     * */
    ForecastDetail requestForecastDetail(Integer orderId);

    /**
     * 请求计算当前价格
     *
     * @param dto 请求当前价格DTO
     * @return 订单价格
     */
    CurrentPriceResponseDto calcCurrentPrice(CurrentPriceRequestDto dto);

    /**
     * 结算订单价格
     *
     * @param orderId 订单id
     * @return 价格
     */
    BigDecimal calcSettlementPrice(Integer orderId);
}
