package com.gyh.apipassenger.gray;

import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class RequestAspect {

    // 拦截器
    @Pointcut("execution(* com.gyh.apipassenger.controller..*Controller*.*(..))")
    private void  anyMethod(){

    }

    @Before(value = "anyMethod()")
    public void before(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String version = request.getHeader("version");

        //ribbon做灰度规则 匹配的地方 查db，redis
        Map<String, String> map = new HashMap<>();
        map.put("version", version);
        RibbonParameters.set(map);
        //灰度规则，匹配的地方 查db，redis
        if (version.trim().equals("v1")) {
            // service-sms 中metadata-map的version的值
            RibbonFilterContextHolder.getCurrentContext().add("version", "v1");
        }
    }
}
