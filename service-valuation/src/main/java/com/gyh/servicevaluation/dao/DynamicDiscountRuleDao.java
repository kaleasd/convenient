package com.gyh.servicevaluation.dao;

import com.gyh.internalcommon.dto.valuation.discount.DiscountCondition;
import com.gyh.internalcommon.dto.valuation.discount.DiscountPrice;
import com.gyh.servicevaluation.mapper.DynamicDiscountRuleMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 动态调价DAO
 * @date 2023/2/15
 * */
@Repository
@RequiredArgsConstructor
public class DynamicDiscountRuleDao {
    @NonNull
    private DynamicDiscountRuleMapper dynamicDiscountRuleMapper;

    /**
     * 根据检索条件查询调价信息
     * @param condition 检索条件
     * @return 调价信息
     * */
    public DiscountPrice findDiscountByCondition(DiscountCondition condition) {
        return dynamicDiscountRuleMapper.findDiscountByCondition(condition);
    }
}
