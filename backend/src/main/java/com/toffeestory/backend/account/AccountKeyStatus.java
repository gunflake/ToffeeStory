package com.toffeestory.backend.account;

public enum AccountKeyStatus {

    UNUSED(0, "Unused"),
    USE(1, "Use");

    private final int value;
    private final String description;

    AccountKeyStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.description;
    }

}
