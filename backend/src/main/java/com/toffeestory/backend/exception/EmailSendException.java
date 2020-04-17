package com.toffeestory.backend.exception;

public class EmailSendException extends RuntimeException {
    public EmailSendException() {
        super("이메일 전송에 문제가 발생했습니다. 다시 시도해주세요.");
    }
}
