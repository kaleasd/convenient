package com.gyh.mapservice.entity;

import lombok.Data;

/**
 * 
 * @param <T>
 */
@Data
public class AmapResult<T> {

	private int status;
	
	private T data;
	
	
}
