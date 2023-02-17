package com.gyh.serviceverificationcode.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CodeVerifyRequest implements Serializable {

    private int identity;
    private String phoneNumber;
    private String code;
}
