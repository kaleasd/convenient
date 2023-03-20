package com.gyh.cloudzuul.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * common_gray_rule
 * @author 
 */
@Data
public class CommonGrayRule implements Serializable {

    private Integer id;

    private Integer userId;

    private String serviceName;

    private String version;

    private static final long serialVersionUID = 1L;
}