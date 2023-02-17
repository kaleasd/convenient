package com.gyh.servicesms.dao;

import com.gyh.servicesms.dao.entity.ServiceSmsRecord;
import com.gyh.servicesms.dao.mapper.ServiceSmsRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gyh
 * */
@Service
public class SmsDao {
    @Autowired
    private ServiceSmsRecordMapper serviceSmsRecordMapper;

    public int insert(ServiceSmsRecord serviceSmsRecord) {
        return serviceSmsRecordMapper.insert(serviceSmsRecord);
    }
}
