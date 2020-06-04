package com.toffeestory.backend.exception;

public class FailEnrollAlarmException extends RuntimeException{
    public FailEnrollAlarmException() {
        super("알람 등록에 실패했습니다.");
    }
}
