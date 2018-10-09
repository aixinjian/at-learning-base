package com.learning.base.exception;

public class BaseRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -6209075113162812539L;
    private static final int DEFAULT_ERROR_CODE = 201;


    /* 错误码,用于返回接口code */
    private int errCode;

    public BaseRuntimeException(){
        super();
    }

    public BaseRuntimeException(String msg) {
        super(msg);
        this.errCode = DEFAULT_ERROR_CODE;
    }
    public BaseRuntimeException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
    }

    public BaseRuntimeException(String msg, Throwable e) {
        super(msg,e);
        this.errCode = DEFAULT_ERROR_CODE;
    }

    public BaseRuntimeException(int errCode, String msg, Throwable e) {
        super(msg, e);
        this.errCode = errCode;
    }


    public int getErrCode() {
        return errCode;
    }
}
