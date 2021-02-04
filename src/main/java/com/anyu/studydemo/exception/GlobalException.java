package com.anyu.studydemo.exception;

import com.anyu.studydemo.model.enums.IResultType;
import org.springframework.http.HttpStatus;

/**
 * 自定义异常类
 *
 * @author Anyu
 * @since 2021/2/2 下午2:58
 */
public class GlobalException extends RuntimeException {

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    private final IResultType resultType;

    private GlobalException(IResultType resultType, HttpStatus httpStatus) {
        super(resultType.getMessage());
        this.message = resultType.getMessage();
        this.code = resultType.getCode();
        this.httpStatus = httpStatus;
        this.resultType = resultType;
    }

    /**
     * api 异常处理结果
     * @param resultType 结果类型
     * @param httpStatus 请求状态
     */
    public static GlobalException causeBy(IResultType resultType, HttpStatus httpStatus) {
        return new GlobalException(resultType, httpStatus);
    }

    /**
     * 业务层处理结果
     * @param resultType 结果类型
     */
    public static GlobalException causeBy(IResultType resultType) {
        return new GlobalException(resultType, null);
    }

    public int getCode() {
        return code;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public IResultType getResultType() {
        return resultType;
    }
}
