package com.gyh.internalcommon.dto.order;

import lombok.Data;

@Data
public class BaseOrder {
	
	String startLatitude;
	
	String startLongitude;
	
	String endLatitude;
	
	String endLongitude;
}