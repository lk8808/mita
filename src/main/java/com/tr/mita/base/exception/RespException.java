package com.tr.mita.base.exception;

public class RespException extends Exception {

    private String errcode;

    public RespException() {
        super();
        this.errcode = "999999";
    }

    public RespException(String msg){
        super(msg);
        this.errcode = "999999";
    }

    public RespException(String code, String msg) {
        super(msg);
        this.errcode = code;
    }

    public String getErrcode() {
        return this.errcode;
    }
}
