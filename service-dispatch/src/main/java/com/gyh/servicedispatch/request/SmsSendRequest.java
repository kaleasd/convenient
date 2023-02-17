package com.gyh.servicedispatch.request;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * @date 2023/2/17
 */
@Data
public class SmsSendRequest {

    private String[] receivers;
    private List<SmsTemplateDto> data;

    @Override
    public String toString() {
        return "SmsSendRequest [reveivers=" + Arrays.toString(receivers) + ", data=" + data + "]";
    }

}
