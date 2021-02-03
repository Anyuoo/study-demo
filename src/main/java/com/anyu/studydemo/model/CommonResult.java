package com.anyu.studydemo.model;

import com.anyu.studydemo.model.enums.IResultType;
import com.anyu.studydemo.model.enums.ResultType;

import java.io.Serializable;
/**
*统一结果封装
* @author Anyu
* @since 2021/2/2 下午3:19
*/
public class CommonResult <T> implements Serializable {
    private boolean success;
    private int code;
    private String message;
    private T data;

    private CommonResult(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功的处理结果
     *
     * @return CommonResult
     */

    public static <T> CommonResult<T> with(IResultType resultType, T data) {
        return new CommonResult<>(resultType.getSuccess(), resultType.getCode(), resultType.getMessage(), data);
    }
    public static <T> CommonResult<T> with(IResultType resultType) {
        return new CommonResult<>(resultType.getSuccess(), resultType.getCode(), resultType.getMessage(), null);
    }





    public boolean getSuccess() {
        return success;
    }

    public CommonResult<T> setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public CommonResult<T>  setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResult<T>  setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonResult<T>  setData(T data) {
        this.data = data;
        return this;
    }
}
