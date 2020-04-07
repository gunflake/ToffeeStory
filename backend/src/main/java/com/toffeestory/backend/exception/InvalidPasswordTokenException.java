package com.toffeestory.backend.exception;

public class InvalidPasswordTokenException extends RuntimeException {
    public InvalidPasswordTokenException() {
        super("토큰 정보가 만료되었거나 유효하지 않는 토큰입니다.");
    }
}
