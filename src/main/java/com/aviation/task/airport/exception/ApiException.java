package com.aviation.task.airport.exception;

import org.springframework.http.HttpStatus;

public class ApiException {

    private final HttpStatus httpStatus;
    private final String msg;

    public ApiException(String msg, HttpStatus httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    public String getMsg() {
        return msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
