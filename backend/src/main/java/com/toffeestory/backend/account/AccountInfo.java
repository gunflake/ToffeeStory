package com.toffeestory.backend.account;

import lombok.Getter;

@Getter
public class AccountInfo {
    private String username;
    private String email;
    private String src;

    public AccountInfo(String username, String email, String src) {
        this.username = username;
        this.email = email;
        this.src = src;
    }
}
