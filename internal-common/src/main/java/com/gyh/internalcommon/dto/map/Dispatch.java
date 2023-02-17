package com.gyh.internalcommon.dto.map;

import lombok.Data;

import java.util.List;

/**
 * 
 */
@Data
public class Dispatch{
	
	private Integer count;
	
	private String orderId;
	
	private List<Vehicle> vehicles;
}