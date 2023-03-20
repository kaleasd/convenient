package com.gyh.apilistenorder.service.impl;

import com.gyh.apilistenorder.response.PreGrabResponse;
import com.gyh.apilistenorder.service.ListenService;
import com.gyh.internalcommon.constant.RedisKeyConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ListenServiceImpl implements ListenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public PreGrabResponse listen(int driverId) {
        String key = RedisKeyConstant.DRIVER_LISTEN_ORDER_PRE + driverId;
        String orderId = redisTemplate.opsForValue().get(key);
        redisTemplate.delete(orderId);
        PreGrabResponse preGrabResponse = new PreGrabResponse();
        try {
            if (StringUtils.isNotBlank(orderId)) {
                preGrabResponse.setEndAddress("终点" + new Random().nextInt());
                preGrabResponse.setStartAddress("起点" + new Random().nextInt());
                preGrabResponse.setOrderId(Integer.parseInt(orderId));
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            return preGrabResponse;
        }
    }
}
