package com.gyh.apilistenorder.response;

import lombok.Data;

/**
 * @author oi
 */
@Data
public class PreGrabResponse {

    private int orderId;

    private String startAddress;

    private String endAddress;


}
