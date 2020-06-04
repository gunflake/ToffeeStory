package com.toffeestory.backend.exception;

public class NotFoundAlarmException extends RuntimeException {

    public NotFoundAlarmException(String seqNo) {
        super(seqNo + "의 알람 메세지가 없습니다.");
    }
}
