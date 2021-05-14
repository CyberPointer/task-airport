package com.aviation.task.airport.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;
    private final String msg;

    public ApiException(String msg,HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        this.msg = msg;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }

    public String getMsg() {
        return msg;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }
}
