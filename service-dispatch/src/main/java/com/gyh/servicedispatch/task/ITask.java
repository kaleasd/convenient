package com.gyh.servicedispatch.task;


import com.gyh.internalcommon.entity.Order;
import com.gyh.internalcommon.entity.OrderRulePrice;

import java.util.List;

/**
 */
public interface ITask {

    int execute(long current);

    int getTaskId();

    boolean isTime();

    int getOrderType();

    boolean sendOrder(Order order, OrderRulePrice orderRulePrice, TaskCondition taskCondition, int round);

    void taskEnd(Order order, OrderRulePrice orderRulePrice);

    void setTaskConditions(List<TaskCondition> taskConditions);
}
