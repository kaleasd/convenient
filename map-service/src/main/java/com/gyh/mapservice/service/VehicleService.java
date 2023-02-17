package com.gyh.mapservice.service;


import com.gyh.internalcommon.dto.map.request.VehicleRequest;

/**
 * 
 */
public interface VehicleService {

	/**
	 * 同步车辆
	 * @param vehicleRequest
	 * @return
	 */
	public String uploadCar(VehicleRequest vehicleRequest);
}
