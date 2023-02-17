package com.gyh.mapservice.service;


import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.Route;
import com.gyh.internalcommon.dto.map.request.DistanceRequest;

/**
 * 距离测量
 */
public interface DistanceService {
	/**
	 * 测量距离
	 * @param distanceRequest
	 * @return
	 */
	ResponseResult<Route> distance(DistanceRequest distanceRequest);
	
}
