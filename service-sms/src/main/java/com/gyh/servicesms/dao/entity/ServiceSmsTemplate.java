package com.gyh.servicesms.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServiceSmsTemplate implements Serializable {

    private Integer id;
    private String templateId;
    private String templateName;
    private String templateContent;
    private Boolean templateType;
    private Date createTime;
    private Date updateTime;
}
