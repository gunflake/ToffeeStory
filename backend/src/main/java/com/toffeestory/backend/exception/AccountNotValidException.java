package com.toffeestory.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountNotValidException extends RuntimeException {
    public AccountNotValidException() {
        super();
    }

    public AccountNotValidException(String message) {
        super(message);
    }

    public AccountNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
