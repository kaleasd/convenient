package com.gyh.servicedispatch.mapper;

import com.gyh.internalcommon.entity.DriverBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface DriverBaseInfoMapper {

    DriverBaseInfo selectByPrimaryKey(Integer id);

}
