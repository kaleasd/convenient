package com.gyh.internalcommon.dto;

import com.gyh.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 通用返回值处理类
 * */

@Data
/**
 * chain的中文含义是链式的，设置为true，则setter方法返回当前对象
 * */
@Accessors(chain = true)
@SuppressWarnings("all")
public class ResponseResult<T> {

    private static final long serialVersionUID = -2073390651767969040L;
    private int code;
    private String message;
    private T data;

    /**
     * 返回成功数据（status：200）
     *
     * @return ResponseResult实例
     */
    public static ResponseResult success() {
        return success(null);
    }

    /**
     * 返回成功数据（status：1）
     *
     * @param data 数据类型
     * @param <T> 数据类型
     * @return ResponseResult实例
     * */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult()
                .setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getValue())
                .setData(data);
    }

    public static <T> ResponseResult fail(T data) {
        return (new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getValue()).setData(data));
    }

    public static ResponseResult fail(int code, String message) {
        return (new ResponseResult().setCode(code).setMessage(message));
    }

    public static ResponseResult fail(int code, String message, String data) {
        return (new ResponseResult().setCode(code).setMessage(message).setData(data));
    }

    public ResponseResult() {
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public ResponseResult<T> setCode(final int code) {
        this.code = code;
        return this;
    }

    public ResponseResult<T> setMessage(final String message) {
        this.message = message;
        return this;
    }

    public ResponseResult<T> setData(final T data) {
        this.data = data;
        return this;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseResult)) {
            return false;
        } else {
            ResponseResult<?> other = (ResponseResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getCode() != other.getCode()) {
                return false;
            } else {
                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseResult;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getCode();
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResponseResult(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
