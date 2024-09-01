package com.tyme.exception;

public class BizException extends RuntimeException{
    int code = 0;
    String message;
    public BizException(String message) {
        this.message = message;
    }
}
