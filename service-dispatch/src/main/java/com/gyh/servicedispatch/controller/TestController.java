package com.gyh.servicedispatch.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @date 2018/8/29
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "dispatch success";
    }
}
