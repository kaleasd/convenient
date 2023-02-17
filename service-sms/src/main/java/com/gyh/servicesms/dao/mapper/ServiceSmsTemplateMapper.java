package com.gyh.servicesms.dao.mapper;

import com.gyh.servicesms.dao.entity.ServiceSmsTemplate;

public interface ServiceSmsTemplateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServiceSmsTemplate record);

    int insertSelective(ServiceSmsTemplate record);

    ServiceSmsTemplate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceSmsTemplate record);

    int updateByPrimaryKey(ServiceSmsTemplate record);
}
