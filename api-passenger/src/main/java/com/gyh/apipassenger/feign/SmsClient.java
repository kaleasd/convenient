package com.gyh.apipassenger.feign;

import com.gyh.apipassenger.fallback.SmsClientFallback;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yueyi2019
 */


//@FeignClient(name = "service-sms",configuration = FeignDisableHystrixConfiguration.class)
//@FeignClient(name = "service-sms",fallbackFactory = SmsClientFallbackFactory.class)
@FeignClient(name = "service-sms",fallback = SmsClientFallback.class)
//@FeignClient(name = "service-sms")
public interface SmsClient {
	/**
	 * 按照短信模板发送验证码
	 * @param smsSendRequest
	 * @return
	 */
	@RequestMapping(value="/send/alisms-template", method = RequestMethod.POST)
	public ResponseResult sendSms(@RequestBody SmsSendRequest smsSendRequest) throws Exception;
}
