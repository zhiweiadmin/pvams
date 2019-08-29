package com.goodpower.pvams.common;

public enum ResultEnums {

    SUCCESS("200", "请求成功"),
    FAIL("400", "请求失败"),
    TOKEN_EXPIRE("302","token已失效"),
    AUTH_ERROR("401", "token验证错误"),
    SERVER_ERROR("500","服务器异常");


    private String code;
    private String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
