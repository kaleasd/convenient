package com.gyh.servicefile.service;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyplsapi.model.v20170525.*;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.gyh.internalcommon.dto.phone.request.PhoneNumberRequest;
import com.gyh.internalcommon.util.TimeUtils;
import com.gyh.servicefile.utils.AliPhoneConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class SecretPhoneNumberService {

    @Autowired
    private AliPhoneConfig aliPhoneConfig;

    /**
     * AXB绑定
     * @param phoneNumberRequest
     * @return
     * @throws com.aliyun.oss.ClientException
     * */
    // public void bindAxb(PhoneNumberRequest phoneNumberRequest) {}
    public BindAxbResponse bindAxb(PhoneNumberRequest phoneNumberRequest) throws com.aliyuncs.exceptions.ClientException {
        IAcsClient acsClient = newClient();
        // 组装请求对象
        BindAxbRequest request = new BindAxbRequest();
        // 必填：对应的号池key
        request.setPoolKey(aliPhoneConfig.getPoolKey());
        // 必填：AXB关系中的A号码
        request.setPhoneNoA(phoneNumberRequest.getDriverPhone());
        // 必填：AXB关系中的B号码
        request.setPhoneNoB(phoneNumberRequest.getPassengerPhone());
        // 必填：绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration(phoneNumberRequest.getExpiration());
        // 可选：是否需要录制音频-默认是false
        request.setOutId("yourOutId");
        return acsClient.getAcsResponse(request);
    }

    /**
     * AXN绑定
     *
     * @return
     * @throws ClientException
     */
    public BindAxbResponse bindAxn(String aPhone, String bPhone, Date expiration) throws ClientException, com.aliyuncs.exceptions.ClientException {
        IAcsClient acsClient = newClient();
        // 组装请求对象-具体描述见控制台-文档部分内容
        BindAxbRequest request = new BindAxbRequest();
        // 必填对应的号池Key
        request.setPoolKey(aliPhoneConfig.getPoolKey());
        // 必填:AXN关系中的A号码
        request.setPhoneNoA(aPhone);
        // 可选:AXN中A拨打X的时候转接到的默认的B号码,如果不需要则不设置
        request.setPhoneNoB(bPhone);
        // 必填:绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration(TimeUtils.getDefaultFormate(expiration));
        // 可选:是否需要录制音频-默认是false
        request.setIsRecordingEnabled(true);
        // 外部业务自定义ID属性
        request.setOutId("yourOutId");

        return acsClient.getAcsResponse(request);
    }

    /**
     * 更新AXB绑定
     *
     * @param subsId     创建绑定关系API接口所返回的订购关系ID
     * @param secretNo   创建绑定关系API接口所返回的X号码
     * @param expiration 绑定关系对应的失效时间-不能早于当前系统时间
     * @return
     * @throws ClientException
     */
    @SneakyThrows
    public UpdateSubscriptionResponse unbindSubscriptionResponse(String subsId, String secretNo, Date expiration) {
        IAcsClient acsClient = newClient();
        // 组装请求对象-具体描述见控制台-文档部分
        UpdateSubscriptionRequest request = new UpdateSubscriptionRequest();
        // 绑定关系对应的号池Key
        request.setPoolKey(aliPhoneConfig.getPoolKey());
        // 必填:绑定关系ID
        request.setSubsId(subsId);
        // 必填：绑定关系对应的X号码
        request.setPhoneNoX(secretNo);
        // 必填:操作类型指令支持
        request.setOperateType("updateExpire");
        // 必填：绑定关系对应的失效时间-不能早于当前系统时间
        request.setExpiration(TimeUtils.getDefaultFormate(expiration));

        return acsClient.getAcsResponse(request);

    }

    /**
     * 关系解绑
     *
     * @return
     * @throws ClientException
     * */
    public UnbindSubscriptionResponse unbind(PhoneNumberRequest phoneNumberRequest) throws com.aliyuncs.exceptions.ClientException {
        IAcsClient acsClient = newClient();
        // 组装请求对象
        UnbindSubscriptionRequest request = new UnbindSubscriptionRequest();
        // 必填：对应的号池Key
        request.setPoolKey(aliPhoneConfig.getPoolKey());
        // 必填：分配的X小号-对应到绑定接口中返回的secretNo;
        request.setSecretNo(phoneNumberRequest.getSecretNo());
        // 必填：绑定关系对应的ID-对应到绑定接口中返回的subsId;
        request.setSubsId(phoneNumberRequest.getSubsId());
        return acsClient.getAcsResponse(request);
    }

    /**
     * 初始化ascClient
     *
     * @return
     * @throws ClientException
     */
    @SneakyThrows
    private IAcsClient newClient() throws ClientException {
        // 可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");

        String key = aliPhoneConfig.get(AliPhoneConfig.KEY);
        IClientProfile profile = DefaultProfile.getProfile("cn-beijing", key, aliPhoneConfig.getSECRET());
        DefaultProfile.addEndpoint("cn-beijing", "cn-beijing", aliPhoneConfig.getPRODUCT(), aliPhoneConfig.getDOMAIN());
        return new DefaultAcsClient(profile);
    }
}
