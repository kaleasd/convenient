package com.gyh.mapservice.constant;

/**
 * 
 * @date 2018/8/20
 */
public enum MapStatusEnum {

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

	public int getCode() {
		return code;
	}
}
