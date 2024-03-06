package com.example.chatapp.common.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends RuntimeException {
    private HttpStatus httpStatus;

    private String code;


    public static CommonException create(HttpStatus httpStatus){
        CommonException exception = new CommonException();
        exception.httpStatus = httpStatus;

        return exception;
    }

    public CommonException code(String code) {
        this.code = code;

        return this;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }
}
