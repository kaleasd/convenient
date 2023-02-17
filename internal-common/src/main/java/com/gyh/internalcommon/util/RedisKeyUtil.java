package com.gyh.internalcommon.util;

import com.gyh.internalcommon.constant.IdentityConstant;
import com.gyh.internalcommon.constant.RedisKeyPrefixConstant;

public class RedisKeyUtil {

    /**
     * 根据身份类型生成对应的缓存key
     * @return
     * */

    public RedisKeyUtil(){}

    public static String generateKeyPreByIdentity(int identity) {
        String keyPre = "";
        if (identity == IdentityConstant.PASSENGER) {
            keyPre = RedisKeyPrefixConstant.PASSENGER_LOGIN_CODE_KEY_PRE;
        } else if (identity == IdentityConstant.DRIVER) {
            keyPre = RedisKeyPrefixConstant.DRIVER_LOGIN_CODE_KEY_PRE;
        }
        return keyPre;
    }
}
