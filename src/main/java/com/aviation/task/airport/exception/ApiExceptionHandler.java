package com.aviation.task.airport.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;


@ControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiExceptionHandler {

    private Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(value = {ApiRequestException.class, MethodArgumentTypeMismatchException.class,
            DateTimeParseException.class, IllegalArgumentException.class, ConversionFailedException.class, ConstraintViolationException.class})
    public ResponseEntity<Object> handleMultipleExceptions(Exception e) {
        log.error("exception handling");
        String msg = "Incorrect parameter/s";
        ApiException apiException = new ApiException(msg, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFound(NotFoundException e) {
        log.error("value not found");
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
}
