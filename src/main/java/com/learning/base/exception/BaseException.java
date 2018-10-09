package com.learning.base.exception;

/**
 * 基础异常
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -6202759931628235239L;

    private static final int DEFAULT_ERROR_CODE = 201;

    /* 错误码,用于返回接口code */
    private int errCode;

    public BaseException(){
        super();
    }

    public BaseException(String msg) {
        super(msg);
        this.errCode = DEFAULT_ERROR_CODE;
    }
    public BaseException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public BaseException(String msg, Throwable e) {
        super(msg,e);
        this.errCode = DEFAULT_ERROR_CODE;
    }

    public BaseException(int errCode, String msg, Throwable e) {
        super(msg, e);
        this.errCode = errCode;
    }


    public int getErrCode() {
        return errCode;
    }
}
