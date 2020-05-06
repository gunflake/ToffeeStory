package com.toffeestory.backend.exception;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException() {
        super("해당 포스트를 찾을 수 없습니다.");
    }

    public NotFoundPostException(int postNo) {
        super(postNo + "번 포스트 정보를 찾을 수 없습니다.");
    }
}
