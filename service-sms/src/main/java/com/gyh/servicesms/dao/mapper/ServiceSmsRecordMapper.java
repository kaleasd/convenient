package com.gyh.servicesms.dao.mapper;

import com.gyh.servicesms.dao.entity.ServiceSmsRecord;

public interface ServiceSmsRecordMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ServiceSmsRecord record);

    int insertSelective(ServiceSmsRecord record);

    ServiceSmsRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServiceSmsRecord record);

    int updateByPrimaryKey(ServiceSmsRecord record);
}
