package com.gyh.apipassenger.gray;

import org.springframework.stereotype.Component;

@Component
public class RibbonParameters {

    private static final ThreadLocal local = new ThreadLocal<>();

    // get
    public static <T> T get(){
        Object o = local.get();
        return (T) o;
    }

    // set
    public static <T> void set(T t){
        local.set(t);
    }
}
