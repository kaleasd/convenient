package com.gyh.apipassenger.controller;

import com.gyh.apipassenger.request.ShortMsgRequest;
import com.gyh.apipassenger.service.VerificationCodeService;
import com.gyh.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verify-code")
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult send(@RequestBody @Validated ShortMsgRequest request) {
        return verificationCodeService.send(request.getPhoneNumber());
    }
}
