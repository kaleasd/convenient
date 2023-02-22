package com.gyh.servicedispatch.task;

import com.gyh.servicedispatch.consts.OrderTypeEnum;
import com.gyh.servicedispatch.task.impl.OrderAirportDropoffTask;
import com.gyh.servicedispatch.task.impl.OrderAirportPickupTask;
import com.gyh.servicedispatch.task.impl.OrderNormalTask;

/**
 * @author gyh
 * @date 2023/2/21
 * */
public class OrderTaskFactory {

    public static ITask createTask(int orderId, int serviceTypeId, int type) {
        if (serviceTypeId == OrderTypeEnum.APPOINTMENT.getCode()) {
            return new OrderNormalTask(orderId, type);
        } else if (serviceTypeId == OrderTypeEnum.AIRPORT_PICKUP.getCode()) {
            return new OrderAirportPickupTask(orderId, type);
        } else if (serviceTypeId == OrderTypeEnum.AIRPORT_DROPOFF.getCode()) {
            return new OrderAirportDropoffTask(orderId, type);
        } else {
            return new OrderNormalTask(orderId, type);
        }
    }
}
