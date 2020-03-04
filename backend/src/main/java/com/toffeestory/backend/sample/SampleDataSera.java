package com.toffeestory.backend.sample;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountService;
import com.toffeestory.backend.product.Product;
import com.toffeestory.backend.product.ProductCategory;
import com.toffeestory.backend.product.ProductCategoryRepository;
import com.toffeestory.backend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class SampleDataSera implements ApplicationRunner {

    @Autowired
    private AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAccount();
    }

    private void createAccount() {
        Account account = new Account();
        account.setEmail("seralee0330@gmail.com");
        account.setAccountId("seralee");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Sera Lee");
        account.setBio("Hello :)");
        account.setInstagram("seralee0330");
        account.setProfilePic("https://randomuser.me/api/portraits/women/21.jpg");

        accountService.saveAccount(account);
    }
}
