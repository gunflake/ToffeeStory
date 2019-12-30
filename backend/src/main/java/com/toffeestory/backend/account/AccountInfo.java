package com.toffeestory.backend.account;

import lombok.Getter;

@Getter
public class AccountInfo {
    private String username;
    private String email;

    public AccountInfo(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
