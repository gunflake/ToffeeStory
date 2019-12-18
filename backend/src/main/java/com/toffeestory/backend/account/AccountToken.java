package com.toffeestory.backend.account;

import lombok.Getter;
import lombok.Setter;

@Getter
public class AccountToken {
    private String username;
    private String token;

    public AccountToken() {
    }

    public AccountToken(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
