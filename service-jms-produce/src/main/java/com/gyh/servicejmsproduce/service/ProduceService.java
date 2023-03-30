package com.gyh.servicejmsproduce.service;

import javax.jms.Destination;

/**
 * @author gyh
 */
public interface ProduceService {

    /**
     * 向指定队列发送消息
     * @param destination
     * @param message
     */
    public void send(Destination destination , String message);
}
