package com.gyh.servicesms.controller;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.servicesms.request.SmsSendRequest;
import com.gyh.servicesms.service.AliService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send")
@Slf4j
public class SendController {
    @Autowired
    private AliService aliService;

    @RequestMapping(value = "/alisms-template", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<String> send(@RequestBody SmsSendRequest smsSendRequest) {
        // 输出收到的参数内容
        JSONObject param = JSONObject.fromObject(smsSendRequest);
        log.info("/send-template   request: " + param.toString());
        return ResponseResult.success("");
    }

}
