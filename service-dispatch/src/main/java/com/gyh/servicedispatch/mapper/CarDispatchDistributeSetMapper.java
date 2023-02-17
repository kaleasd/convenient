package com.gyh.servicedispatch.mapper;

import com.gyh.internalcommon.entity.CarDispatchDistributeSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarDispatchDistributeSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchDistributeSet record);

    int insertSelective(CarDispatchDistributeSet record);

    CarDispatchDistributeSet selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarDispatchDistributeSet record);

    int updateByPrimaryKey(CarDispatchDistributeSet record);

    CarDispatchDistributeSet getOpenedByCityCode(@Param("cityCode") String cityCode);
}
