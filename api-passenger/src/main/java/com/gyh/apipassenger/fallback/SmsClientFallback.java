package com.gyh.apipassenger.fallback;

import com.gyh.apipassenger.feign.SmsClient;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class SmsClientFallback implements SmsClient {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseResult sendSms(SmsSendRequest smsSendRequest) throws Exception {
        log.debug("出现错误，熔断！！！");

		String key = "service-sms";
		String noticeString = redisTemplate.opsForValue().get(key);
		if(StringUtils.isBlank(noticeString)) {
			//发送短信，或者调用电话接口
			System.out.println("通知别人我熔断了");
			redisTemplate.opsForValue().set(key, "notice", 30, TimeUnit.SECONDS);

		}else {
			System.out.println("通知过了，我先不通知了");
		}
        throw new RuntimeException("异常");
//		return ResponseResult.fail(-3, "feign熔断");
    }
}
