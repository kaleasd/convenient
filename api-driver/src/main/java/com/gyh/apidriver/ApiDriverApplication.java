package com.gyh.apidriver;

import com.gyh.apidriver.annotation.ExcudeRibbonConfig;
import com.gyh.apidriver.ribbonconfig.RibbonConfiguration;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrix
// 扫描不包活 注解修饰的类
@ComponentScan(
        basePackages = {"com.gyh"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = ExcudeRibbonConfig.class)}
)
// 下面是让所有client都实现随机策略
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@RibbonClient(name = "service-sms", configuration = RibbonConfiguration.class)
public class ApiDriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDriverApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 手写简单ribbon
     * */

}
