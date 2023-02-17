package com.gyh.mapservice.service;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.request.DispatchRequest;

/**
 * @author gyh
 * */
public interface DispatchService {
    /**
     * 调度车辆
     * @param
     * @return
     * */
    ResponseResult dispatch(DispatchRequest dispatchRequest);
}
