package com.toffeestory.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.ResponseEntity.badRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {InvalidAccountException.class})
    public ResponseEntity vehicleNotFound(InvalidAccountException ex, WebRequest request) {
        log.error("handling NotValidAccountException...");
        return badRequest().body(new RestApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

}
