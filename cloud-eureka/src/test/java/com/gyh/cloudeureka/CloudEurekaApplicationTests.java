package com.gyh.cloudeureka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayDeque;
import java.util.Deque;

@SpringBootTest
class CloudEurekaApplicationTests {

    @Test
    void contextLoads() {
        Deque<String> deque = new ArrayDeque<>();
        deque.add("1");
        deque.add("2");
        deque.add("3");
        System.out.println(deque);
        deque.offerFirst("b");
        System.out.println(deque);
        deque.addFirst("a");
        System.out.println(deque);
        deque.addLast("z");
        System.out.println(deque);
    }

}
