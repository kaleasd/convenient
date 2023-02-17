package com.gyh.internalcommon.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import java.util.Date;

public class OrderIdUtils {
    public synchronized static String getOrderNumber() {
        StringBuffer stringBuffer = new StringBuffer();
        String time = DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
        String mcode = System.getProperty("mcode");
        String randomString = RandomStringUtils.randomNumeric(2);

        return stringBuffer.append(time).append(mcode).append(randomString).toString();
    }
}
