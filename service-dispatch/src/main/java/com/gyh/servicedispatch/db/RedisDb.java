package com.gyh.servicedispatch.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Redis封装类
 * @author gyh
 * @date 2023/2/17
 * */
@Repository
public class RedisDb {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<String ,String> redisTemplate;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, String value, long expireTime) {
        stringRedisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public String getSet(String key, String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public boolean setnx(String key, String value, int second) {
        Boolean aBoolean = redisTemplate.opsForValue().setIfPresent(key, value);
        if (aBoolean) {
            redisTemplate.expire(key, second, TimeUnit.SECONDS);
        }
        return aBoolean;
    }
}
