package com.gyh.internalcommon.constant;

/**
 * 通用枚举接口
 * @author gyh
 * @date 2023/2/15
 * */
public interface CodeEnum {
    /**
     * 返回枚举体的code值
     * @return code值
     * */
    int getCode();

    /**
     * 返回枚举体的code值
     * @return 字符串格式的code值
     * */
    default String getCodeAsString() {
        return "" + getCode();
    }
}
