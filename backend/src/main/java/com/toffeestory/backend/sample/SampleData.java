package com.toffeestory.backend.sample;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.account.AccountService;
import com.toffeestory.backend.post.Post;
import com.toffeestory.backend.post.PostDtl;
import com.toffeestory.backend.post.PostDtlRepository;
import com.toffeestory.backend.post.PostRepository;
import com.toffeestory.backend.product.Product;
import com.toffeestory.backend.product.ProductCategory;
import com.toffeestory.backend.product.ProductCategoryRepository;
import com.toffeestory.backend.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class SampleData implements ApplicationRunner {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostDtlRepository postDtlRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createAccountHM();
    }

    private void createAccountHM() {
        Account account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setInstagram("test007");
        account.setTwitter("test009");
        account.setAccountName("Vincent Nam");
        accountService.saveAccount(account);

        Account account1 = new Account();
        account1.setEmail("seralee0330@gmail.com");
        account1.setAccountId("seralee");
        account1.setAccountPwd("qwer1234");
        account1.setInstagram("test007");
        account1.setTwitter("test009");
        account1.setAccountName("Sera Lee");
        accountService.saveAccount(account1);

        Account account2 = new Account();
        account2.setEmail("yuri@gmail.com");
        account2.setAccountId("yuri");
        account2.setAccountPwd("qwer1234");
        account2.setInstagram("happy_oriday");
        account2.setTwitter("twitter");
        account2.setAccountName("Yuri");
        accountService.saveAccount(account2);

        Post post = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post.setAccount(account);
        post.setSrc("postpic1.jpg");
        post.setContent("content");
        post.setScore(3.0f);
        post.setLikeCnt(10);
        post.setPrice((short)2000);

        List<String> tag = new ArrayList<>();
        tag.add("아메리카노");
        tag.add("카페라떼");
        post.setTags(tag);

        postRepository.save(post);

        Post post2 = new Post();
        account.setPosts(postRepository.findAllByAccount(account));
        post2.setAccount(account);
        post2.setSrc("postpic2.jpg");
        post2.setContent("content2");
        post2.setScore(4.0f);
        post2.setLikeCnt(20);
        post2.setPrice((short)1000);

        List<String> tag2 = new ArrayList<>();
        tag2.add("아메리카노");
        post2.setTags(tag2);

        postRepository.save(post2);

        PostDtl postdtl2 = new PostDtl();
        postdtl2.setPostNo(2);
        postdtl2.setFlag((byte)1);
        postdtl2.setToffeeKey(1);
        postdtl2.setTagName("아메리카노");

        postDtlRepository.save(postdtl2);

        PostDtl postdtl3 = new PostDtl();
        postdtl3.setPostNo(3);
        postdtl3.setFlag((byte)1);
        postdtl3.setToffeeKey(3);
        postdtl3.setTagName("바닐라 라떼");

        postDtlRepository.save(postdtl3);

    }
}
