package com.toffeestory.backend.exception;

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
