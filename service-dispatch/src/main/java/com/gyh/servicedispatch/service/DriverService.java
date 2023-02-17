package com.gyh.servicedispatch.service;

import com.gyh.internalcommon.entity.DriverInfo;

import java.util.Date;

public interface DriverService {

    DriverInfo getDriverById(int id);

    DriverInfo getDriverByCarId(int carId);

    void updateDriverInfo(DriverInfo driverInfo);

    int getWorkDriverCount(String city, int carType, Date startTime, Date endTime);

    Double getDriverEvaluateByDriverId(int driverId);

    int getDriverOrderCount(int driverId);
}
