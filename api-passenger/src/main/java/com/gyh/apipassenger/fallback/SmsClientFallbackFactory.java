package com.gyh.apipassenger.fallback;

import com.gyh.apipassenger.feign.SmsClient;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsClientFallbackFactory implements FallbackFactory<SmsClient> {
    @Override
    public SmsClient create(Throwable cause) {
        return new SmsClient() {
            @Override
            public ResponseResult sendSms(SmsSendRequest smsSendRequest) throws Exception {
                log.debug("feign异常：" + cause);
                return ResponseResult.fail(-3, "feign fallback factory熔断");
            }
        };
    }
}
