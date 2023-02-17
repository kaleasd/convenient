package com.gyh.servicedispatch.request;

import lombok.Data;

/**
 * @date 2023/2/17
 */
@Data
public class DistanceRequest {
	
	private String originLongitude;
	
	private String originLatitude;
	
	private String destinationLongitude;
	
	private String destinationLatitude;
	
}
