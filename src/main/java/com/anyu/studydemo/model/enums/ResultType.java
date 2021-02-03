package com.anyu.studydemo.model.enums;

public enum ResultType implements IResultType {
    SUCCESS(true, 2000, "操作成功"),
    FAILED(false, 4000, "操作失败"),
    ;

    private final Boolean success;
    private final int code;
    private final String message;

    ResultType(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
