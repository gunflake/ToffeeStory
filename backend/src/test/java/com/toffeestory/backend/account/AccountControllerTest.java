package com.toffeestory.backend.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountService accountService;

    @PersistenceContext
    EntityManager entityManager;


    @BeforeEach
    public void saveAccount() throws Exception{
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");
        accountService.saveAccount(account);
        entityManager.flush();
    }

    @Test
    public void testCreateMember() throws Exception{

        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");

        this.mockMvc.perform(
                post("/api/accounts")
                        .content("{\n" +
                                "      \"accountName\": \"vincent\",\n" +
                                "      \"accountId\": \"vincent\",\n" +
                                "      \"email\": \"vincent@gmail.com\",\n" +
                                "      \"accountPwd\": \"qwer1234\"\n" +
                                "}")
        .contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isCreated());

        entityManager.flush();

        verify(accountRepository, times(1)).save(any(Account.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    public void testLoginMember() throws Exception {



        this.mockMvc.perform(
                post("/api/accounts/login")
                        .content("{\n" +
                                "      \"email\": \"gunflake09@gmail.com\",\n" +
                                "      \"accountPwd\": \"qwer1234\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountRepository, times(1)).save(any(Account.class));
        verifyNoMoreInteractions(accountRepository);

    }


}
