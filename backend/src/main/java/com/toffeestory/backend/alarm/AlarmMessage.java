package com.toffeestory.backend.alarm;

import lombok.Getter;

@Getter
public class AlarmMessage {
    String image;
    String message;

    public AlarmMessage(String image, String message) {
        this.image = image;
        this.message = message;
    }
}
