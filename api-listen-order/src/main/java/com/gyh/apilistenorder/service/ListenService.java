package com.gyh.apilistenorder.service;


import com.gyh.apilistenorder.response.PreGrabResponse;

public interface ListenService {
    /**
     *
     * @param driverId
     * @return
     */
    public PreGrabResponse listen(int driverId);
}
