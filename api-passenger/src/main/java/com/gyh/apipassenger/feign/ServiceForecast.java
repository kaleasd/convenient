package com.gyh.apipassenger.feign;

import com.gyh.apipassenger.feign.config.FeignAuthConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name = "service-valuation", configuration = FeignAuthConfiguration.class)
@FeignClient(name = "service-valuation")
public interface ServiceForecast {
}
