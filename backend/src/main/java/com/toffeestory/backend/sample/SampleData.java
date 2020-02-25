package com.toffeestory.backend.sample;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.account.AccountService;
import com.toffeestory.backend.post.Post;
import com.toffeestory.backend.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class SampleData implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAccountHM();

    }

    private void createAccountHM() {
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");

        accountService.saveAccount(account);

        Post post = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post.setAccount(account);
        post.setPostPic("postPic");
        post.setContent("content");
        post.setScore(3.0f);
        post.setLikeCnt(10);
        post.setPrice((short)1000);

        postRepository.save(post);
    }
}
