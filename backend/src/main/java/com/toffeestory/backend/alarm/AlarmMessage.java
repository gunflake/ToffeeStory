package com.toffeestory.backend.alarm;

import lombok.Getter;

@Getter
public class AlarmMessage {
    String accountId;
    String image;
    String message;
    Integer alarmSeqNo;

    public AlarmMessage(String accountId, String image, String message, Integer alarmSeqNo) {
        this.accountId = accountId;
        this.image = image;
        this.message = message;
        this.alarmSeqNo = alarmSeqNo;
    }
}
