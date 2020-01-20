package com.toffeestory.backend.account;

import com.toffeestory.backend.exception.NotFoundAccountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    public AccountRepository accountRepository;

    @BeforeEach
    void createAccount() {
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");
        accountRepository.save(account);
    }

    //중복키 삽입 테스트 > 에러 발생
    @Test
    void duplicateConstraint(){
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");
        accountRepository.save(account);

        Account account1 = accountRepository.findByAccountId("gunflake09").orElseThrow(() -> new NotFoundAccountException("gunflake09"));
    }

    @Test
    void findByAccountId() {
        Account account = accountRepository.findByAccountId("gunflake09").orElseThrow(() -> new NotFoundAccountException("gunflake09"));
        assertEquals("gunflake09", account.getAccountId());
        assertEquals("gunflake09@gmail.com", account.getEmail());
        assertEquals("qwer1234", account.getAccountPwd());
    }

    @Test
    void findByEmail() {
        Account account = accountRepository.findByEmail("gunflake09@gmail.com").orElseThrow(() -> new NotFoundAccountException("gunflake09@gmail.com"));
        assertEquals("gunflake09", account.getAccountId());
        assertEquals("gunflake09@gmail.com", account.getEmail());
        assertEquals("qwer1234", account.getAccountPwd());
    }
}
