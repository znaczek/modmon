package com.wz.modularmonolithexample.shared.exceptions;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleException(Throwable e, HttpServletRequest request) {
        log.error("An error ocurred", e);
        var responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ResponseStatus annotationResponseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        if (annotationResponseStatus != null) {
            responseStatus = annotationResponseStatus.value();
        }

        if (e instanceof IllegalStateException || e instanceof IllegalArgumentException) {
            responseStatus = HttpStatus.BAD_REQUEST;
        }
        var res = new CustomErrorResponse(responseStatus, e.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(res, responseStatus);
    }

}
