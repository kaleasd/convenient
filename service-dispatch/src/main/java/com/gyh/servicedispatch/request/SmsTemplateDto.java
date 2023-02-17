package com.gyh.servicedispatch.request;

import lombok.Data;

import java.util.Map;

/**
 * @date 2023/2/17
 */
@Data
public class SmsTemplateDto {

    private String id;

    private Map<String, Object> templateMap;

    @Override
    public String toString() {
        return "SmsTemplateDto [id=" + id + ", templateMap=" + templateMap + "]";
    }

}
