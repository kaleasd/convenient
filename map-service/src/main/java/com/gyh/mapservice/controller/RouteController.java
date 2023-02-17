package com.gyh.mapservice.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.Distance;
import com.gyh.internalcommon.dto.map.Points;
import com.gyh.internalcommon.dto.map.request.RouteRequest;
import com.gyh.mapservice.service.RouteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/20
 */
@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping(value = "distance")
    public ResponseResult distance(RouteRequest routeRequest) {

        String vehicleId = routeRequest.getVehicleId();
        String city = routeRequest.getCity();
        Long startTime = routeRequest.getStartTime();
        Long endTime = routeRequest.getEndTime();
        Distance distance = routeService.getRoute(vehicleId, city, startTime, endTime);

        return ResponseResult.success(distance);
    }

    @GetMapping(value = "points")
    public ResponseResult points(RouteRequest routeRequest) {

        String vehicleId = routeRequest.getVehicleId();
        String city = routeRequest.getCity();
        Long startTime = routeRequest.getStartTime();
        Long endTime = routeRequest.getEndTime();
        String correction = routeRequest.getCorrection();
        Points amapPointsResponse;
        if (StringUtils.isNotBlank(correction) && correction.trim().equals("origin")) {
            amapPointsResponse = routeService.getPointsAllPage(vehicleId, city, startTime, endTime, "");
        } else {
            amapPointsResponse = routeService.getPointsAllPage(vehicleId, city, startTime, endTime, "driving");
        }

        return ResponseResult.success(amapPointsResponse);
    }


}
