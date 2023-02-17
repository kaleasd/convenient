package com.gyh.servicesms.dao;

import com.gyh.servicesms.dao.entity.ServiceSmsTemplate;
import com.gyh.servicesms.dao.mapper.ServiceSmsTemplateCustomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gyh
 * */
@Service
public class SmsTemplateDao {
    @Autowired
    private ServiceSmsTemplateCustomMapper customMapper;

    public ServiceSmsTemplate getByTemplateId(Integer templateId) {
        return customMapper.selectByPrimaryKey(templateId);
    }
}
