package com.toffeestory.backend.exception;

public class MaxUploadSizeExceededException extends RuntimeException {
    public MaxUploadSizeExceededException() {
        super("업로드할 수 있는 이미지 최대 크기는 5MB입니다.");
    }

}
