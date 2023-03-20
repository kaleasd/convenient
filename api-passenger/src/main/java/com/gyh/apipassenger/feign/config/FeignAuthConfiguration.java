package com.gyh.apipassenger.feign.config;

import com.gyh.apipassenger.annotation.ExcudeFeignConfig;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

@ExcudeFeignConfig
public class FeignAuthConfiguration {
    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("root", "root");
    }
}
