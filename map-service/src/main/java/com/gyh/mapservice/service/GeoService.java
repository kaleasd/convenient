package com.gyh.mapservice.service;


import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.Geo;
import com.gyh.internalcommon.dto.map.request.GeoRequest;

/**
 * @date 2018/9/14
 */
public interface GeoService {

    ResponseResult<Geo> getCityCode(GeoRequest geoRequest);

}
