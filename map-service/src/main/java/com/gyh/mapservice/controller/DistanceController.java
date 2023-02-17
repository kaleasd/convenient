package com.gyh.mapservice.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.request.DistanceRequest;
import com.gyh.mapservice.service.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/20
 */
@RestController
public class DistanceController {

    @Autowired
    private DistanceService distanceService;

    @GetMapping(value = "/distance")
    public ResponseResult distance(DistanceRequest distanceRequest) {
        return distanceService.distance(distanceRequest);
    }
}
