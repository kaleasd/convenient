package com.gyh.servicedispatch.service.impl;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.Dispatch;
import com.gyh.internalcommon.dto.map.Route;
import com.gyh.internalcommon.dto.map.request.OrderRequest;
import com.gyh.internalcommon.dto.phone.BoundPhoneDto;
import com.gyh.internalcommon.dto.push.PushLoopBatchRequest;
import com.gyh.internalcommon.dto.push.PushRequest;
import com.gyh.internalcommon.entity.Order;
import com.gyh.internalcommon.sms.SmsRequest;
import com.gyh.internalcommon.util.RestTemplateHepler;
import com.gyh.servicedispatch.request.DispatchRequest;
import com.gyh.servicedispatch.request.DistanceRequest;
import com.gyh.servicedispatch.request.SmsSendRequest;
import com.gyh.servicedispatch.request.SmsTemplateDto;
import com.gyh.servicedispatch.service.ConfigService;
import com.gyh.servicedispatch.service.HttpService;
import com.gyh.servicedispatch.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gyh
 * @date 2023/2/17
 * */
@Service
@Slf4j
public class HttpServiceImpl implements HttpService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConfigService configService;

    @Override
    public ResponseResult sendSms(String phone, String smsCode, Map<String, Object> templateMap) {
        String url = configService.messageServiceUrl() + "/sms/send";
        SmsSendRequest request = new SmsSendRequest();
        String[] receivers = new String[1];
        receivers[0] = phone;
        request.setReceivers(receivers);
        List<SmsTemplateDto> list = new ArrayList<>();
        SmsTemplateDto dto = new SmsTemplateDto();
        dto.setId(smsCode);
        dto.setTemplateMap(templateMap);
        list.add(dto);
        request.setData(list);

        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

    @Override
    public ResponseResult sendSms(String phone, String smsCode, String... content) {
        String url = configService.messageServiceUrl() + "/sms/hx_send";
        SmsRequest request = new SmsRequest();
        request.setPhones(new String[]{phone});
        request.setTemplateId(smsCode);
        request.setContent(content);

        ResponseResult responseResult = restTemplate.postForObject(url, request, ResponseResult.class);

        return responseResult;
    }

    @Override
    public ResponseResult updateAmapOrder(OrderRequest orderRequest) {
        String url = configService.mapServiceUrl() + "/order";
        ResponseResult response = restTemplate.postForObject(url, orderRequest, ResponseResult.class);
        return response;
    }

    @Override
    public ResponseResult<Dispatch> dispatch(DispatchRequest dispatchRequest) {
        String url = configService.mapServiceUrl() + "/vehicleDispatch";
        ResponseResult response = restTemplate.postForObject(url, dispatchRequest, ResponseResult.class);
        try {
            log.info(response.toString());
            Dispatch o = RestTemplateHepler.parse(response, Dispatch.class);
            return ResponseResult.success(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public double calDistance(DistanceRequest distanceRequest) {
        String url = configService.mapServiceUrl() + "/distance?";
        Map<String, Object> map = new HashMap<>();
        map.put("originLongitude", distanceRequest.getOriginLongitude());
        map.put("originLatitude", distanceRequest.getOriginLatitude());
        map.put("destinationLongitude", distanceRequest.getDestinationLongitude());
        map.put("destinationLatitude", distanceRequest.getDestinationLatitude());
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        url = url + param;
        double distance = Integer.MAX_VALUE;
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, map);
        if (response.getData() != null) {
            try {
                log.info(response.toString());
                Route o = RestTemplateHepler.parse(response, Route.class);
                distance = o.getDistance();
            } catch (Exception e) {
                log.error("distance error " + e.getMessage());
            }
        }
        return distance;
    }

    @Override
    public void unbind(String subId, String secretNo) {
        String url = configService.fileServiceUrl() + "phone/unbind?";
        Map<String, Object> map = new HashMap<>();
        map.put("subsId", subId);
        map.put("secretNo", secretNo);
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        url = url + param;
        log.info("unbind url " + url);
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, map);
        log.info("unbind response " + response);
    }

    @Override
    public BoundPhoneDto bind(String phone1, String phone2, long expireTime) {
        String url = configService.fileServiceUrl() + "/phone/bind?";
        Map<String, Object> map = new HashMap<>();
        map.put("driverPhone", phone1);
        map.put("passengerPhone", phone2);
        map.put("expiration", DateUtils.formatDate(new Date(expireTime), DateUtils.DEFAULT_TIME_FORMAT));
        String param = String.join("&", map.keySet().stream().map(k -> k + "={" + k + "}").collect(Collectors.toList()));
        url = url + param;
        log.info("bind " + url);
        ResponseResult response = restTemplate.getForObject(url, ResponseResult.class, map);
        log.info("bind result " + response.toString());
        if (response.getData() != null) {
            log.info(response.toString());
            try {
                BoundPhoneDto data = RestTemplateHepler.parse(response, BoundPhoneDto.class);
                return data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean updateOrder(Order order) {
        String url = configService.orderServiceUrl() + "/order/updateOrder";
        ResponseResult response = restTemplate.postForObject(url, order, ResponseResult.class);
        if (response.getCode() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public ResponseResult pushMsg(PushRequest pushRequest) {
        String url = configService.messageServiceUrl() + "/push/message";
        ResponseResult response = restTemplate.postForObject(url, pushRequest, ResponseResult.class);
        return response;
    }

    @Override
    public ResponseResult loopMessage(PushLoopBatchRequest request) {
        String url = configService.messageServiceUrl() + "/loop/message";
        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }

    @Override
    public ResponseResult loopMessageBatch(PushLoopBatchRequest request) {
        String url = configService.messageServiceUrl() + "/loop/batch/message";
        ResponseResult response = restTemplate.postForObject(url, request, ResponseResult.class);
        return response;
    }
}
