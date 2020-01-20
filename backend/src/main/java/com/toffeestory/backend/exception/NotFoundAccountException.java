package com.toffeestory.backend.exception;

public class NotFoundAccountException extends RuntimeException {
    public NotFoundAccountException() {
    }

    public NotFoundAccountException(String message) {
        super(message + " 회원 정보를 찾을 수 없습니다.");
    }

}
