package com.gyh.apipassenger.service.impl;

import com.gyh.apipassenger.service.ServiceSmsRestTemplateService;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.SmsTemplateDto;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ServiceSmsRestTemplateServiceImpl implements ServiceSmsRestTemplateService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public ResponseResult sendSms(String phoneNumber, String code) {
        String http = "http://";
        String serviceName = "SERVICE-SMS";
        String uri = "/send/sms-template";
        String url = http + serviceName + uri;
        SmsSendRequest smsSendRequest = new SmsSendRequest();
        String[] phoneNumbers = new String[] {phoneNumber};
        smsSendRequest.setReceivers(phoneNumbers);
        List<SmsTemplateDto> data = new ArrayList<SmsTemplateDto>();
        SmsTemplateDto dto = new SmsTemplateDto();
        dto.setId("SMS_144145499");
        int templateSize = 1;
        HashMap<String, Object> templateMap = new HashMap<>(templateSize);
        templateMap.put("code", code);
        dto.setTemplateMap(templateMap);
        data.add(dto);
        smsSendRequest.setData(data);
        return restTemplate
                .postForEntity(url, smsSendRequest, ResponseResult.class)
                .getBody();
    }
}
