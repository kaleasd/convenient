package com.gyh.mapservice.service;


import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.mapservice.entity.FenceCreateEntity;
import com.gyh.mapservice.response.AmapFenceInResponse;
import com.gyh.mapservice.response.AmapFenceSearchResponse;

import java.util.List;

/**
 * 
 */
public interface FenceService {

	AmapFenceInResponse isInFence(String longitude, String latitude, String diu);

	ResponseResult createFence(String gid, String name, String points , String description, String validTime, String enable);

	FenceCreateEntity changeStatus(String gid , String enable);

	AmapFenceSearchResponse searchFence(String id, String gid, String name, String pageNo, String pageSize, String enable,
										String startTime, String endTime);

	ResponseResult searchFence(List<String> gids);

	Boolean delFence(String gid);
}
