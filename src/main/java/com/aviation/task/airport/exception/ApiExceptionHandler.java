package com.aviation.task.airport.exception;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;


@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiExceptionHandler{

    public final HttpStatus badRequest = HttpStatus.BAD_REQUEST;

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, DateTimeParseException.class,IllegalArgumentException.class,ConversionFailedException.class})
    public ResponseEntity<Object> handleArgumentTypeMismatchException(Exception e) {
        String msg = "Incorrect argument/s";
        ApiException apiException = new ApiException(msg, badRequest, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, badRequest);
    }
}
