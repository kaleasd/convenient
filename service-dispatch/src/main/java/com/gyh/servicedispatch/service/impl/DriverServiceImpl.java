package com.gyh.servicedispatch.service.impl;

import com.gyh.internalcommon.entity.DriverInfo;
import com.gyh.servicedispatch.mapper.DriverInfoMapper;
import com.gyh.servicedispatch.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverInfoMapper driverInfoMapper;


    @Override
    public DriverInfo getDriverById(int id) {
        return driverInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public DriverInfo getDriverByCarId(int carId) {
        return driverInfoMapper.selectByCarId(carId);
    }

    @Override
    public void updateDriverInfo(DriverInfo driverInfo) {
        driverInfoMapper.updateByPrimaryKeySelective(driverInfo);
    }

    @Override
    public int getWorkDriverCount(String city, int carType, Date startTime, Date endTime) {
        return driverInfoMapper.countWorkDriver(city, carType, startTime, endTime);
    }

    @Override
    public Double getDriverEvaluateByDriverId(int driverId) {
        return driverInfoMapper.getDriverEvaluateByDriverId(driverId);
    }

    @Override
    public int getDriverOrderCount(int driverId) {
        return driverInfoMapper.getDriverOrderCount(driverId);
    }
}
