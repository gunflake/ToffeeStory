package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    Account account;
    int postNo = 0;

    @PersistenceUnit
    EntityManagerFactory emf;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void createAccount() {
        account = new Account();
        account.setEmail("gunflake09@gmail.com");
        account.setAccountId("gunflake09");
        account.setAccountPwd("qwer1234");
        account.setAccountName("Vincent Nam");
        //account.setPosts()
        accountRepository.save(account);


        em.persist(account);
    }

    @BeforeEach
    void createPost() {
        Post post = new Post();
        Account gunflake09 = accountRepository.findByAccountId("gunflake09").orElseThrow(() -> new RuntimeException());
        //gunflake09.getPost();
        if(gunflake09.getPosts() == null){
            System.out.println("null 값");
        }
        gunflake09.setPosts(postRepository.findAllByAccount(gunflake09));


        post.setAccount(gunflake09);
        post.setSrc("@/assets/image/main_img1.jpg");
        post.setContent("게시글 입니다.");
        post.setScore(4.0f);
        post.setLikeCnt(10);
        post.setPrice((short)200);
        postRepository.save(post);

        em.persist(post);
        postNo = post.getPostNo();
    }
    @Test
    void findByPostNo() {
        List<Post> findPost = postRepository.findAll();
        System.out.println("----------------");
        for (int i = 0; i < findPost.size(); i++) {
            System.out.println(findPost.get(i).getSrc());
        }
        System.out.println("----------------");
    }
}
