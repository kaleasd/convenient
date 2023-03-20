package com.gyh.servicefile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@MapperScan("com.gyh.mapper")
public class ServiceFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceFileApplication.class, args);
    }

}
