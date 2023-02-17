package com.gyh.mapservice.constant;

import com.gyh.internalcommon.constant.CodeEnum;

/**
 * 
 * @date 2023/2/20
 */
public enum MapStatusEnum implements CodeEnum {

    /**
     * 围栏已存在
     */
    FENCE_ALREAD(106, "新增围栏已存在"),


	EX(999,"none");
    private final int code;
    private final String value;

    MapStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
	public int getCode() {
		return code;
	}
}
