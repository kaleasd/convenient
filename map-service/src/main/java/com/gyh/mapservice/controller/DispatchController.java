package com.gyh.mapservice.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.request.DispatchRequest;
import com.gyh.mapservice.service.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/20
 */
@RestController
public class DispatchController {

	@Autowired
	private DispatchService dispatchService;
	
	@PostMapping("/vehicleDispatch")
	public ResponseResult dispatch(@RequestBody DispatchRequest dispatchRequest) {
		return dispatchService.dispatch(dispatchRequest);
	}
}
