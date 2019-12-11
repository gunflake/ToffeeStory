package com.toffeestory.backend.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(path = "/create")
    public Account createMember(@RequestBody Account account){
        System.out.println(account.toString());
        return accountRepository.save(account);
    }
}
