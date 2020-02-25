package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PostDtlRepository postDtlRepository;

    @Autowired
    InterestPostRepository interestPostRepository;

    /*-------------------------------
       -select Post List (new / best / hot)
     -------------------------------*/
    @GetMapping(path = "")
    public List<Post> initPage(@RequestParam("flag") Integer flag) {
        if(flag == 0){                      // New
            return postRepository.findAll();
        } else if(flag == 1) {              // Best
            return postRepository.findAllByOrderByScoreDesc();
        } else {                            // Hot
            return postRepository.findAllByOrderByLikeCntDesc();
        }
    }

    /*-------------------------------
       - select Post
     -------------------------------*/
    @GetMapping(path = "/{postNo}")
    public Post selectPost(@PathVariable("postNo") Integer postNo) {
        Post post = postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
        List<String> tagNames = new ArrayList<>();

        List<PostDtl> postDtls = postDtlRepository.findByPostNo(postNo);

        for (int i = 0; i < postDtls.size(); i++) {
            tagNames.add(postDtls.get(i).getTagName());
        }

        post.setTags(tagNames);

        return post;
    }

    /*-------------------------------
      -select Related Post List
    -------------------------------*/
    @GetMapping("/{postNo}/relatedPost")
    public List<Post> relatedPostList(@PathVariable("postNo") Integer postNo) {
        List<Post> posts = new ArrayList<>();

        //해당 게시글이 가지고 있는 토핑, 상품 조회
        List<PostDtl> postDtls = postDtlRepository.findByPostNo(postNo);

        for (int i = 0; i < postDtls.size(); i++) {
            // 토핑, 상품을 가지고 있는 게시글 넘버 가져옴
            List<PostDtl> setPostNo = postDtlRepository.findByTagName(postDtls.get(i).getTagName());

            for (int j = 0; j < setPostNo.size(); j++) {
                int getPostNo = setPostNo.get(i).getPostNo();

                if(!posts.contains(getPostNo)) {
                    posts.add(postRepository.findByPostNo(getPostNo).orElseThrow(() -> new RuntimeException()));
                }
            }
        }

        return posts;
    }

    /*-------------------------------
       - select Tag
     -------------------------------*/
    @GetMapping("/{tagName}")
    public List<Post> tagPostList(@PathVariable("tagName") String tagName) {
        List<Post> posts = new ArrayList<>();
        List<PostDtl> postNo = postDtlRepository.findByTagName(tagName);

        for (int i = 0; i < postNo.size(); i++) {
            posts.add(postRepository.findByPostNo(postNo.get(i).getPostNo()).orElseThrow(() -> new RuntimeException()));
        }

        return posts;
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
