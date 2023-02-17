package com.gyh.mapservice.response;

import com.gyh.mapservice.entity.FenceEntity;
import lombok.Data;

import java.util.List;

@Data
public class AmapFenceSearchResponse {
	
	private List<FenceEntity> list;
	private int count;
	
	public AmapFenceSearchResponse(List<FenceEntity> list, int count) {
		this.list = list;
		this.count = count;
	}
	public AmapFenceSearchResponse() {

	}
}
