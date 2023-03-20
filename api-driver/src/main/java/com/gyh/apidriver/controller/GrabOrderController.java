package com.gyh.apidriver.controller;

import com.gyh.apidriver.service.RestTemplateRequestService;
import com.gyh.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author oi
 */
@RestController
@RequestMapping("/grab")
public class GrabOrderController {

	@Autowired
	private RestTemplateRequestService restTemplateRequestService;
    
    @GetMapping("/do/{orderId}")
    public ResponseResult grab(@PathVariable("orderId") int orderId, int driverId){
    	restTemplateRequestService.grabOrder(orderId,driverId);
        return ResponseResult.success("");
    }
}