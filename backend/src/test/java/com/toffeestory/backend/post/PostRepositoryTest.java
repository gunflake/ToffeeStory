package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public PostRepository postRepository;

    Account account;
    @BeforeEach
    public void saveAccount(){
        account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");

        accountRepository.save(account);
    }

    @Test
    public void savePost(){
        Post post = new Post();
        post.setAccount(accountRepository.findByAccountId("gunflake09").orElseThrow(() -> new RuntimeException()));
        post.setContent("아메리카노 2샷 1헤이즐넛 추천합니다.");
        post.setPostPic("https://dfjsdf");
        post.setScore(4.5f);
        post.setPrice((short) 10000);

        postRepository.save(post);
        Post gunflake09 = postRepository.findByAccount(account).orElseThrow(()->new RuntimeException());
        System.out.println(gunflake09.toString());
        assertEquals(0, gunflake09.getLikeCount().intValue());
        assertEquals(1, gunflake09.getUseStateCode().intValue());
        assertEquals("아메리카노 2샷 1헤이즐넛 추천합니다.", gunflake09.getContent());
    }



}
