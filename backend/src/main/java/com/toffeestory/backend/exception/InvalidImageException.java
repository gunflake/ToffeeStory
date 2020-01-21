package com.toffeestory.backend.exception;

public class InvalidImageException extends RuntimeException {

    public InvalidImageException() {
    }

    public InvalidImageException(String message) {
        super(message);
    }
}
