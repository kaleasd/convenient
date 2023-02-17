package com.gyh.servicedispatch.service.impl;

import com.gyh.internalcommon.entity.*;
import com.gyh.internalcommon.util.ServiceAddress;
import com.gyh.servicedispatch.mapper.*;
import com.gyh.servicedispatch.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
/**
 * */
@Service
@Slf4j
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private CarDispatchDistributeSetMapper carDispatchDistributeSetMapper;
    @Autowired
    private CarDispatchDirectRouteOrderRadiusSetMapper carDispatchDirectRouteOrderRadiusSetMapper;
    @Autowired
    private CarDispatchDistributeIntervalSetMapper carDispatchDistributeIntervalSetMapper;
    @Autowired
    private CarDispatchCapacitySetMapper carDispatchCapacitySetMapper;
    @Autowired
    private CarDispatchTimeThresholdSetMapper carDispatchTimeThresholdSetMapper;
    @Autowired
    private CarDispatchDistributeRadiusSetMapper carDispatchDistributeRadiusSetMapper;
    @Autowired
    private CarDispatchSpecialPeriodSetMapper carDispatchSpecialPeriodSetMapper;
    @Autowired
    private ServiceAddress serviceAddress;


    @Override
    public String mapServiceUrl() {
        return serviceAddress.get("map");
    }

    @Override
    public String messageServiceUrl() {
        return serviceAddress.get("message");
    }

    @Override
    public String fileServiceUrl() {
        return serviceAddress.get("file");
    }

    @Override
    public String orderServiceUrl() {
        return serviceAddress.get("order");
    }

    @Override
    public Integer getGoHomeDistance(String cityCode, int serviceTypeId, int type) {
        CarDispatchDirectRouteOrderRadiusSet carDispatchDirectRouteOrderRadiusSet = carDispatchDirectRouteOrderRadiusSetMapper.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId, type);
        if (carDispatchDirectRouteOrderRadiusSet != null) {
            return carDispatchDirectRouteOrderRadiusSet.getDirectRouteOrderRadius();
        }
        return null;
    }

    @Override
    public CarDispatchCapacitySet getCarDispatchCapacitySet(String cityCode, int timeType) {
        return carDispatchCapacitySetMapper.getCarDispatchCapacitySet(cityCode, timeType);
    }

    @Override
    public List<CarDispatchCapacitySet> getCarDispatchCapacitySetList(String cityCode) {
        return carDispatchCapacitySetMapper.getCarDispatchCapacitySetList(cityCode);
    }

    @Override
    public CarDispatchDistributeRadiusSet getCarDispatchDistributeRadiusSet(String cityCode, int serviceTypeId) {
        return carDispatchDistributeRadiusSetMapper.getCarDispatchDistributeRadiusSet(cityCode, serviceTypeId);
    }

    @Override
    public CarDispatchDirectRouteOrderRadiusSet getCarDispatchDirectRouteOrderRadiusSet(String cityCode, int serviceTypeId, int type) {
        return carDispatchDirectRouteOrderRadiusSetMapper.getCarDispatchDirectRouteOrderRadiusSet(cityCode, serviceTypeId, type);
    }

    @Override
    public CarDispatchDistributeIntervalSet getCarDispatchDistributeIntervalSet(String cityCode, int serviceTypeId) {
        return carDispatchDistributeIntervalSetMapper.selectByCityCodeAndServiceType(cityCode, serviceTypeId);
    }

    @Override
    public boolean isOpenForceSendOrder(String city) {
        CarDispatchDistributeSet carDispatchDistributeSet = carDispatchDistributeSetMapper.getOpenedByCityCode(city);
        if (null != carDispatchDistributeSet) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSpecial(String cityCode, int serviceCode, long time) {
        CarDispatchSpecialPeriodSet carDispatchSpecialPeriodSet = carDispatchSpecialPeriodSetMapper.getByCityCode(cityCode, serviceCode);
        if (carDispatchSpecialPeriodSet == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        JSONArray array = JSONArray.fromObject(carDispatchSpecialPeriodSet.getTimePeriod());
        for (int i = 0; i < array.size(); i++) {
            JSONObject o = array.getJSONObject(i);
            String start = o.getString("start");
            String end = o.getString("end");
            if (isInTimePeriod(hour, minute, start, end)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInTimePeriod(int hour, int minute, String start, String end) {
        double t = Double.valueOf(hour + "." + minute);
        double s = Double.valueOf(start.replace(":", "."));
        double e = Double.valueOf(end.replace(":", "."));
        if (t >= s && t <= e) {
            return true;
        }
        return false;
    }

    @Override
    public int getForceSendOrderTime(String cityCode, int type) {
        CarDispatchTimeThresholdSet carDispatchTimeThresholdSet = carDispatchTimeThresholdSetMapper.selectByCityAndServiceType(cityCode, type);
        return carDispatchTimeThresholdSet.getTimeThreshold();
    }
}
