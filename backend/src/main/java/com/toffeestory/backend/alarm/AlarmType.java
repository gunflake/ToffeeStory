package com.toffeestory.backend.alarm;

public enum AlarmType {
    LIKE(0, "00님이 회원님의 게시물을 좋아합니다.");

    private final int value;
    private final String description;

    AlarmType(int value, String description) {
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
