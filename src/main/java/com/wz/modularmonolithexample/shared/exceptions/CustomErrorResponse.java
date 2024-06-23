package com.wz.modularmonolithexample.shared.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomErrorResponse {

    private int status;

    private String message;

    private String error;

    private String timestamp;

    private String path;

    public CustomErrorResponse(HttpStatus status, String message, String path) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = Instant.now().toString();
        this.path = path;
    }
}
