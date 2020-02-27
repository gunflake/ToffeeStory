package com.toffeestory.backend.exception;

public class NotFoundImageException extends RuntimeException {
    public NotFoundImageException(String message) {
        super(message+" 파일을 찾을 수 없습니다.");
    }
}
