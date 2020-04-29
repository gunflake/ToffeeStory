package com.toffeestory.backend.exception;

public class NotfoundEmailException extends RuntimeException{
    public NotfoundEmailException(String message) {
        super(message+" 이메일로 가입한 회원 정보가 없습니다.");
    }
}
