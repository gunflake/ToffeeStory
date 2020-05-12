package com.toffeestory.backend.exception;

public class InvalidEncryptTextException extends RuntimeException {
    public InvalidEncryptTextException() {
        super("복호화에 실패했습니다. 암호화 text를 확인해주세요");
    }
}
