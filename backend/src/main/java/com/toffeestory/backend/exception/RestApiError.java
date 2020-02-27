package com.toffeestory.backend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

public class RestApiError {

    @JsonFormat
    private HttpStatus status;

    @JsonFormat
    private String message;

    public RestApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
