package com.toffeestory.backend.exception;

public class InvalidPostException extends RuntimeException {
    public InvalidPostException() {
        super();
    }

    public InvalidPostException(String message) {
        super(message);
    }

    public InvalidPostException(String message, Throwable cause) {
        super(message, cause);
    }
}
