package com.learning.base.exception;

public class ArgumentException extends BaseRuntimeException {
    private static final long serialVersionUID = -6209025113112222539L;


    public ArgumentException(){
        super();
    }

    public ArgumentException(String msg) {
        super(msg);
    }

    public ArgumentException(int errCode, String msg) {
        super(errCode, msg);
    }

    public ArgumentException(String msg, Throwable e) {
        super(msg,e);
    }

    public ArgumentException(int errCode, String msg, Throwable e) {
        super(errCode, msg, e);
    }
}
