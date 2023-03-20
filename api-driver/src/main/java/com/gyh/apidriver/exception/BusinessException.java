package com.gyh.apidriver.exception;

import com.netflix.hystrix.exception.HystrixBadRequestException;
/**
 * 在提供者是好的，但是消费者出现异常时，不希望走熔断的备用方案
 * 自定义异常，继承HystrixBadRequestException，当出现异常时，不走备用方法
 * */
public class BusinessException extends HystrixBadRequestException {

    private String message;

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
