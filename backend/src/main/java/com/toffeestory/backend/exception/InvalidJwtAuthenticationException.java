package com.toffeestory.backend.exception;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException() {
        super("JWT 토큰이 만료됐거나 유효하지 않습니다.");
    }
}
