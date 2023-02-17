package com.gyh.servicedispatch.data;

import com.gyh.internalcommon.dto.map.Vehicle;
import com.gyh.internalcommon.entity.CarInfo;
import com.gyh.internalcommon.entity.DriverInfo;
import lombok.Data;

/**
 */
@Data
public class DriverData {
    private Vehicle amapVehicle;
    private DriverInfo driverInfo;
    private double homeDistance;
    private CarInfo carInfo;
    private int isFollowing;
    private int timeSort;
}
