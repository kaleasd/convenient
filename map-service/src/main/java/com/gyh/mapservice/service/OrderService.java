package com.gyh.mapservice.service;


import com.gyh.internalcommon.dto.map.request.OrderRequest;

/**
 * 
 */
public interface OrderService {

	/**
	 * 同步订单
	 * @param orderRequest
	 * @return
	 */
	public String order(OrderRequest orderRequest) ;
}
