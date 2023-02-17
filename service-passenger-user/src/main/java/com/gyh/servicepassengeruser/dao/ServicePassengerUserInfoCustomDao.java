package com.gyh.servicepassengeruser.dao;

import com.gyh.servicepassengeruser.entity.ServicePassengerUserInfo;

public interface ServicePassengerUserInfoCustomDao extends ServicePassengerUserInfoDao{

    /**
     * 根据手机号查询乘客信息
     * @param passengerPhone
     * @return
     * */
    ServicePassengerUserInfo selectByPhoneNumber(String passengerPhone);
}
