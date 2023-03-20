package com.gyh.cloudzuul.dao;

import com.gyh.cloudzuul.entity.CommonGrayRule;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonGrayRuleDaoCustom extends CommonGrayRuleDao {

    CommonGrayRule selectByUserId(Integer userId);
}
