package com.toffeestory.backend.account;

import com.toffeestory.backend.alarm.AlarmMessage;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountInfo {
    private String username;
    private String email;
    private String src;
    private List<AlarmMessage> alarmMessageList;

    public AccountInfo(String username, String email, String src, List<AlarmMessage> alarmMessageList) {
        this.username = username;
        this.email = email;
        this.src = src;
        this.alarmMessageList = alarmMessageList;
    }
}
