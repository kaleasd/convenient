package com.gyh.mapservice.service.impl;

import com.gyh.internalcommon.dto.ResponseResult;
import com.gyh.internalcommon.dto.map.Geo;
import com.gyh.internalcommon.dto.map.request.GeoRequest;
import com.gyh.mapservice.constant.AmapResultConfig;
import com.gyh.mapservice.constant.AmapUrlConfig;
import com.gyh.mapservice.service.GeoService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @date 2018/9/14
 */
@Service
@Slf4j
public class GeoServiceImpl implements GeoService {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseResult<Geo> getCityCode(GeoRequest geoRequest) {

        String longitude = geoRequest.getLongitude();
        String latitude = geoRequest.getLatitude();

        StringBuilder urlBuild = new StringBuilder();
        urlBuild.append(AmapUrlConfig.REGEO_URL);
        urlBuild.append("?key=" + amapKey);
        urlBuild.append("&location=" + longitude + "," + latitude);
        urlBuild.append("&radius=2");
        urlBuild.append("&extensions=base");
        String url = urlBuild.toString();
        log.info("高德地图：逆地理位置  请求信息：" + url);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        log.info("高德地图：逆地理位置  返回信息：" + responseEntity.getBody());
        Geo geo = addressParse(responseEntity);

        return ResponseResult.success(geo);
    }

    private Geo addressParse(ResponseEntity<String> responseEntity) {
        Geo geo = new Geo();
        String body = responseEntity.getBody();
        JSONObject result = JSONObject.fromObject(body);
        int status = 0;
        if (result.has(AmapResultConfig.AMAP_STATUS)) {
            status = result.getInt(AmapResultConfig.AMAP_STATUS);
        }
        if (status == 1) {
            if (result.has(AmapResultConfig.REGEOCODE)) {
                JSONObject regeoCode = result.getJSONObject(AmapResultConfig.REGEOCODE);
                if (regeoCode.has(AmapResultConfig.ADDRESS_COMPONENT)) {
                    JSONObject addressComponent = regeoCode.getJSONObject(AmapResultConfig.ADDRESS_COMPONENT);
                    if (addressComponent.has(AmapResultConfig.CITY_CODE)) {
                        String cityCode = addressComponent.getString(AmapResultConfig.CITY_CODE);
                        geo.setCityCode(cityCode);
                    }
                }
                if(regeoCode.has(AmapResultConfig.FORMATEED_ADDRESS)){
                    String formateedAddress = regeoCode.getString(AmapResultConfig.FORMATEED_ADDRESS);
                    geo.setFormateedAddress(formateedAddress);

                }
            }
        }
        return geo;
    }
}
