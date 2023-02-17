package com.gyh.servicedispatch.service;

import com.gyh.internalcommon.entity.CarDispatchCapacitySet;
import com.gyh.internalcommon.entity.CarDispatchDirectRouteOrderRadiusSet;
import com.gyh.internalcommon.entity.CarDispatchDistributeIntervalSet;
import com.gyh.internalcommon.entity.CarDispatchDistributeRadiusSet;

import java.util.List;

public interface ConfigService {

    String mapServiceUrl();

    String messageServiceUrl();

    String fileServiceUrl();

    String orderServiceUrl();

    Integer getGoHomeDistance(String cityCode, int serviceTypeId, int type);

    CarDispatchCapacitySet getCarDispatchCapacitySet(String cityCode, int timeType);

    List<CarDispatchCapacitySet> getCarDispatchCapacitySetList(String cityCode);

    CarDispatchDistributeRadiusSet getCarDispatchDistributeRadiusSet(String cityCode, int serviceTypeId);

    CarDispatchDirectRouteOrderRadiusSet getCarDispatchDirectRouteOrderRadiusSet(String cityCode, int serviceTypeId, int type);

    CarDispatchDistributeIntervalSet getCarDispatchDistributeIntervalSet(String cityCode, int serviceTypeId);

    boolean isOpenForceSendOrder(String city);

    boolean isSpecial(String cityCode, int serviceCode, long time);

    boolean isInTimePeriod(int hour, int minute, String start, String end);

    int getForceSendOrderTime(String cityCode, int type);
}
