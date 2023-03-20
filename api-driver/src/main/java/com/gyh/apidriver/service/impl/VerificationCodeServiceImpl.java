package com.gyh.apidriver.service.impl;

import com.gyh.apidriver.service.VerificationCodeService;
import com.gyh.internalcommon.constant.IdentityConstant;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.serviceverificationcode.response.VerifyCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yueyi2019
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

	@Autowired
	private RestTemplate restTemplate;
	
	private final String SERVICE_VERIFICATION_CODE_SERVICE = "service-verification-code";
	
	@Override
	public String getCode(String phoneNumber) {
		String url = "http://"+SERVICE_VERIFICATION_CODE_SERVICE+"/verify-code/generate/"+ IdentityConstant.DRIVER+ "/" +phoneNumber;
		ResponseResult result = restTemplate.exchange(url, HttpMethod.GET,new HttpEntity<Object>(null,null),ResponseResult.class).getBody();

		if(result.getCode()==1) {
			JSONObject data = JSONObject.fromObject(result.getData().toString());
			VerifyCodeResponse response = (VerifyCodeResponse)JSONObject.toBean(data,VerifyCodeResponse.class);
			return response.getCode();
		}else {
			return "";
		}
	}

	@Override
	public String checkCode(String phoneNumber, String code) {
		return null;
	}

}
