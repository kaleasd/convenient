package com.gyh.cloudzuul.dao;


import com.gyh.cloudzuul.entity.CommonGrayRule;

public interface CommonGrayRuleDao {

    int deleteByPrimaryKey(Integer id);

    int insert(CommonGrayRule record);

    int insertSelective(CommonGrayRule record);

    CommonGrayRule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CommonGrayRule record);

    int updateByPrimaryKey(CommonGrayRule record);
}