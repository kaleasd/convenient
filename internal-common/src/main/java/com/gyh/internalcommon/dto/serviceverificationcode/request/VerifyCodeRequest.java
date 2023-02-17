package com.gyh.internalcommon.dto.serviceverificationcode.request;

import lombok.Data;

/**
 * @author gyh
 * */
@Data
public class VerifyCodeRequest {
    private int identity;

    private String phoneNumber;

    private String code;
}
