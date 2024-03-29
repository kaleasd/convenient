package com.gyh.servicedispatch.mapper;

import com.gyh.internalcommon.entity.CarDispatchTimeThresholdSet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 */
@Mapper
public interface CarDispatchTimeThresholdSetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarDispatchTimeThresholdSet record);

    int insertSelective(CarDispatchTimeThresholdSet record);

    CarDispatchTimeThresholdSet selectByPrimaryKey(Integer id);

    /**
     * 查询车辆调度
     * @param cityCode
     * @param type
     * @return
     */
    CarDispatchTimeThresholdSet selectByCityAndServiceType(@Param("cityCode") String cityCode, @Param("type") int type);

    int updateByPrimaryKeySelective(CarDispatchTimeThresholdSet record);

    int updateByPrimaryKey(CarDispatchTimeThresholdSet record);
}
