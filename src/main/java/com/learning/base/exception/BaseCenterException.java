package com.learning.base.exception;

/**
 * 中心基础异常
 */
public class BaseCenterException extends Exception {

    private static final long serialVersionUID = -6202719903688235239L;

    private static final int DEFAULT_ERROR_CODE = 201;


    /* 错误码,用于返回接口code */
    private int errCode;

    public BaseCenterException(){
        super();
    }

    public BaseCenterException(String msg) {
        super(msg);
        this.errCode = DEFAULT_ERROR_CODE;
    }
    public BaseCenterException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public BaseCenterException(String msg, Throwable e) {
        super(msg,e);
        this.errCode = DEFAULT_ERROR_CODE;
    }

    public BaseCenterException(int errCode, String msg, Throwable e) {
        super(msg, e);
        this.errCode = errCode;
    }


    public BaseCenterException(BaseCenterException e) {
        super(e.getMessage(), e);
        this.errCode = e.getErrCode();
    }


    public int getErrCode() {
        return errCode;
    }
}
