package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostToppingDtlRepository postToppingDtlRepository;

    @Autowired
    InterestPostRepository interestPostRepository;

    /*-------------------------------
       -select Post List
     -------------------------------*/
    @GetMapping("/")
    public List<Post> initPage() {
        return postRepository.findAll();
    }

    /*-------------------------------
       - select Post
     -------------------------------*/
    @GetMapping("/{postNo}")
    public Post selectPost(@PathVariable("postNo") Integer postNo) {
        return postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
    }

    /*-------------------------------
      -select Related Post List
    -------------------------------*/
    @GetMapping("/{postNo}/relatedPost")
    public List<Post> relatedPostList(@PathVariable("postNo") Integer postNo) {
        List<Integer> posts = new ArrayList<>();
        List<PostToppingDtl> postToppingDtls = postToppingDtlRepository.findByPostNo(postNo);
        List<Integer> products = new ArrayList<>();
        List<Integer> toppings = new ArrayList<>();

        for (int i = 0; i < postToppingDtls.size(); i++) {
            int productNo = postToppingDtls.get(i).getProductNo();
            int toppingNo = postToppingDtls.get(i).getToppingNo();

            //posts.add(postToppingDtlRepository.findByProductNoOrToppingNo(productNo, toppingNo));
        }

        return postRepository.findAll();
    }

    /*-------------------------------
       - update linkCnt
     -------------------------------*/
    @PostMapping("/{postNo}/like")
    public Post updateLikeCnt(@PathVariable("postNo") Integer postNo, @AuthenticationPrincipal Account account) {
        Post post = postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
        InterestPost likePost = new InterestPost();

        likePost.setAccountNo(account.getAccountNo());
        likePost.setPostNo(postNo);
        likePost.setValueCode((byte)0);

        post.setLikeCnt(post.getLikeCnt()+ 1);

        interestPostRepository.save(likePost);

        return postRepository.save(post);
    }

    /*-------------------------------
      -insert Bookmark Post
    -------------------------------*/
    @PostMapping("/{postNo}/bookmark")
    public void bookmarkPost(@PathVariable("postNo") Integer postNo, @AuthenticationPrincipal Account account) {
        InterestPost bookmarkPost = new InterestPost();

        bookmarkPost.setAccountNo(account.getAccountNo());
        bookmarkPost.setPostNo(postNo);
        bookmarkPost.setValueCode((byte)1);

        interestPostRepository.save(bookmarkPost);
    }
}
