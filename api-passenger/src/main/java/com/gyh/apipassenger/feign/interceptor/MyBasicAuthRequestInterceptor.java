package com.gyh.apipassenger.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class MyBasicAuthRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 有工具类，可以将root，root转换成下面字符串
        requestTemplate.header("Authorization", "Basic cm9vdDpyb290");
    }
}
