package com.gyh.servicedispatch.mapper;

import com.gyh.internalcommon.entity.ChargeRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ChargeRuleMapper {
    ChargeRule getChargeRule(@Param("cityCode") String cityCode, @Param("serviceTypeId") int serviceTypeId, @Param("carLevel") int carLevel);
}
