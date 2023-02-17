package com.gyh.mapservice.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.request.VehicleRequest;
import com.gyh.mapservice.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 */
@RestController
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
    @RequestMapping(value = "/vehicle",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult vehicle(@RequestBody VehicleRequest vehicleRequest){
    	
    	vehicleService.uploadCar(vehicleRequest);
    	return ResponseResult.success(null);
    }
}
