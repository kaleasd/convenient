package com.gyh.apidriver.ribbonconfig;

import com.gyh.apidriver.annotation.ExcudeRibbonConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 该类不应该在主应用程序的扫描下，需要修改启动类的扫描配置
 * */
@Configuration
@ExcudeRibbonConfig
public class RibbonConfiguration {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * 修改IRule
     * @return
     * */
    @Bean
    public IRule ribbonRule() {
        // return new RandomRule();         // 随机策略：随机选择一个服务器
        // return new RetryRule();          // 重试策略：
        // return new RoundRobinRule();     // 轮询策略：简单轮询选择一个服务器。按顺序循环选择一个server
        // return new ZoneAvoidanceRule();  // 区域权衡策略：复合判断Server所在区域的性能和Server的可用性，轮询选择服务器
        return new BestAvailableRule();     // 最低并发策略：先过滤由于多次访问故障而处于短路跳闸状态的服务，然后选择一个并发量最小的服务。逐个找服务，然后断路器打开，则忽略。
    }

    /**
     * 自定义rule
     * @return
     * */
//    @Bean
//    public IRule ribbonRule(){
//        return new MsbRandomRule();
//    }
}
