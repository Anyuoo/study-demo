package com.anyu.studydemo.exception;

import com.anyu.studydemo.util.GlobalConstant;

/**
*
 * 自定义异常类
* @author Anyu
* @since 2021/2/2 下午2:58
*/
public class GlobalException extends RuntimeException{

    private int code;
    private String message;

    public GlobalException(int code, String message) {
        super(message);
        this.code = code;
    }



    public int getCode() {
        return code;
    }


    @Override
    public String getMessage() {
        return message;
    }

}
