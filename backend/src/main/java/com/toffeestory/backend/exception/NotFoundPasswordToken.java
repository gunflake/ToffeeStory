package com.toffeestory.backend.exception;

public class NotFoundPasswordToken extends RuntimeException {
    public NotFoundPasswordToken() {
        super("토큰 정보가 만료되었거나 유효하지 않는 토큰입니다. 비밀번호 재설정을 원하신다면 다시 시도해주세요.");
    }
}
