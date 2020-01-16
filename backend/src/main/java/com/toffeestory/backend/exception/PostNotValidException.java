package com.toffeestory.backend.exception;

public class PostNotValidException extends RuntimeException {
    public PostNotValidException() {
        super();
    }

    public PostNotValidException(String message) {
        super(message);
    }

    public PostNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
