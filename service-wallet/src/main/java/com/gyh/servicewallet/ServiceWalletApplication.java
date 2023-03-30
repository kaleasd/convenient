package com.gyh.servicewallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceWalletApplication.class, args);
    }

}
