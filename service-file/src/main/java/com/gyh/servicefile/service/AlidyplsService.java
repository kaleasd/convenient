package com.gyh.servicefile.service;


import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.servicefile.dto.CallRecordsRequestDto;

/**
 * 功能描述
 *
 * @date 2018/8/21
 */

public interface AlidyplsService {
    ResponseResult callRecords(CallRecordsRequestDto[] callRecordsRequestDto) throws Exception;
}
