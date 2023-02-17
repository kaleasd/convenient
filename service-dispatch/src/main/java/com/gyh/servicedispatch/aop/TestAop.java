package com.gyh.servicedispatch.aop;

import com.gyh.servicedispatch.config.Cache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author gyh
 * */
@Aspect
@Component
@Slf4j
public class TestAop {

    @Before(value = "@annotation(cache)")
    public void before(JoinPoint joinPoint, Cache cache) {
        log.info("cache---------------------------------------++++++++++++++++++++++++++++++++++++++++++" + joinPoint.getSignature().getName());
    }
}
