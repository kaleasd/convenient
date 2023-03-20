package com.gyh.servicevaluation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.gyh.mapper")
// OpenFeign相关启动
@EnableFeignClients
public class ServiceValuationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceValuationApplication.class, args);
    }

}
