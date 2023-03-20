package com.gyh.apidriver.service;


import com.gyh.internalcommon.dto.ResponseResult;

/**
 * @author yueyi2019
 */
public interface ShortMsgService {
	/**
	 * 发送验证码
	 * @param phonenumber
	 * @param code
	 * @return
	 */
	ResponseResult send(String phonenumber, String code);
	
	
}
