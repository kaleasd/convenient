package com.gyh.servicedispatch.mapper;

import com.gyh.internalcommon.entity.PassengerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PassengerInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_passenger_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_passenger_info
     *
     * @mbggenerated
     */
    int insert(PassengerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_passenger_info
     *
     * @mbggenerated
     */
    int insertSelective(PassengerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_passenger_info
     *
     * @mbggenerated
     */
    PassengerInfo selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_passenger_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PassengerInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_passenger_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PassengerInfo record);
}