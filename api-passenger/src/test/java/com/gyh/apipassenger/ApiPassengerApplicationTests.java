package com.gyh.apipassenger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ApiPassengerApplicationTests {

    @Test
    void contextLoads() {
    }

    /**
     * redis guava
     * */
    LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .initialCapacity(2)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "load: " + new Random().nextInt(100);
                }
            });

}
