package com.gyh.servicefile.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gyh.internalcommon.constant.BusinessInterfaceStatus;
import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.servicefile.dto.CallRecordsRequestDto;
import com.gyh.servicefile.service.impl.AlidyplsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通话录音
 * @date 2018/8/21
 */
@RestController
@RequestMapping("/alidypls")
@Slf4j
public class CallRecordingController {
    @Autowired
    private AlidyplsServiceImpl alidyplsService;
    /**
     * [{"phone_no":"18911752116","pool_key":"FC100000032650600","sub_id":1045960989,"call_time":"2018-07-12 11:07:20","peer_no":"17710662549","release_dir":1,"ring_time":"2018-07-12 11:07:26","call_id":"445b46c5e85ea851","start_time":"2018-07-12 11:07:31","partner_key":"FC100000032650600","id":3783248,"secret_no":"17196634061","out_id":"yourOutId","call_type":0,"release_cause":16,"release_time":"2018-07-12 11:07:43"}]
     * @param callRecordsRequestDto
     * @return
     */
    @PostMapping("/callRecord")
    public ResponseResult callRecord(@RequestBody CallRecordsRequestDto[] callRecordsRequestDto) {
        log.info("callRecordsRequestDto={}",callRecordsRequestDto[0]);
        if(null == callRecordsRequestDto){
            return ResponseResult.fail(BusinessInterfaceStatus.FAIL.getCode(), "json为空");
        }
        try {
            ResponseResult responseResult = alidyplsService.callRecords(callRecordsRequestDto);
            return responseResult;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseResult.success(null);
    }

    /**
     * 判断字符串是否可以转化为json对象
     * @param content
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isJsonObject(String content) {
        if(StringUtils.isBlank(content)) {
            return false;
        }
        try {
            JSONObject jsonStr = JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 判断字符串是否可以转化为JSON数组
     * @param content
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isJsonArray(String content) {
        if(StringUtils.isBlank(content)) {
            return false;
        }
        StringUtils.isEmpty(content);
        try {
            JSONArray jsonStr = JSONArray.parseArray(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
