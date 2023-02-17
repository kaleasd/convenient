package com.gyh.servicesms.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ServiceSmsRecord implements Serializable {

    private Integer id;
    private String phoneNumber;
    private String smsContent;
    private Date sendTime;
    private String operatorName;
    private Integer sendFlag;
    private Integer sendNumber;
    private Date createTime;
    private Date updateTime;
}
