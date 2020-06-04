package com.toffeestory.backend.alarm;

public enum AlarmStatus {
    UNREAD(0, "unread message"),
    READ(1, "read message");

    private final int value;
    private final String description;

    AlarmStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
