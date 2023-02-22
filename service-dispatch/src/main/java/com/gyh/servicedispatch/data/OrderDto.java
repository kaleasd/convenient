package com.gyh.servicedispatch.data;


import com.gyh.internalcommon.entity.Order;

/**
 * @date 2023/2/21
 */
public class OrderDto extends Order {
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private Integer orderId;

    private Integer updateType;

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }
}
