package com.gyh.servicedispatch.config;

import com.gyh.servicedispatch.lock.RedisLock;
import com.gyh.servicedispatch.service.DispatchService;
import com.gyh.servicedispatch.service.impl.DispatchServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 */

@Configuration
public class BeanConfig {
    @Bean
    public DispatchService dispatchService() {
        return DispatchServiceImpl.ins();
    }

    @Bean
    public RedisLock redisLock() {
        return RedisLock.ins();
    }
}
