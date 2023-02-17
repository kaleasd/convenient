package com.gyh.servicesms.service.impl;

import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.gyh.internalcommon.dto.servicesms.SmsTemplateDto;
import com.gyh.servicesms.dao.SmsDao;
import com.gyh.servicesms.dao.SmsTemplateDao;
import com.gyh.servicesms.constant.*;
import com.gyh.servicesms.dao.entity.ServiceSmsRecord;
import com.gyh.servicesms.dao.entity.ServiceSmsTemplate;
import com.gyh.servicesms.service.AliService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AliServiceImpl implements AliService {

    /**
     * 缓存用于替换内容的模板
     * */
    private Map<Integer, String> templateMaps = new HashMap<>();
    @Autowired
    private SmsTemplateDao smsTemplateDao;
    @Autowired
    private SmsDao smsDao;

    @Override
    public int sendSms(SmsSendRequest request) throws Exception {
        log.info(request.toString());
        for (String phoneNumber : request.getReceivers()) {
            List<SmsTemplateDto> templates = request.getData();
            ServiceSmsRecord sms = new ServiceSmsRecord();
            sms.setPhoneNumber(phoneNumber);
            for (SmsTemplateDto template : templates) {
                // 从DB加载模板内容至缓存
                if (!templateMaps.containsKey(template.getId())) {
                    // 此处注释掉的内容，将DB模板加载到内存
                    ServiceSmsTemplate t = smsTemplateDao.getByTemplateId(template.getId());
                    log.info(t.getTemplateContent());
                    templateMaps.put(template.getId(), smsTemplateDao.getByTemplateId(template.getId()).getTemplateContent());
                }
                // 替换占位符
                String content = templateMaps.get(template.getId());
                for (Map.Entry<String, Object> entry : template.getTemplateMap().entrySet()) {
                    content = StringUtils.replace(content, "${" + entry.getKey() + "}", ""+entry.getValue());
                }
                // 发生错误时，不影响其他手机号和模板的发送
                try {
                    int result = send(phoneNumber, template.getId(), template.getTemplateMap());
                    // 组装SMS对象
                    sms.setSendTime(new Date());
                    sms.setOperatorName("");
                    sms.setSendFlag(1);
                    sms.setSendNumber(0);
                    sms.setSmsContent(content);
                    if (result != SmsStatusEnum.SEND_SUCCESS.getCode()) {
                        throw new Exception("短信发送失败");
                    }
                } catch (Exception e) {
                    sms.setSendFlag(0);
                    sms.setSendNumber(1);
                    log.error("发送短信（" + template.getId() + ")失败：" + phoneNumber, e);
                } finally {
                    sms.setCreateTime(new Date());
                    smsDao.insert(sms);
                }
            }
        }
        return 0;
    }

    private int send(String phoneNumber, Integer id, Map<String, ?> templateMap) {
        JSONObject param = new JSONObject();
        param.putAll(templateMap);
        return sendMsg(phoneNumber, id, param.toString());
    }

    private int sendMsg(String phoneNumber, Integer templateCode, String param) {
        /**
         * 按照短信供应商的api编写即可
         * */
        return SmsStatusEnum.SEND_SUCCESS.getCode();
    }
}
