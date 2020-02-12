package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.account.AccountRepository;
import com.toffeestory.backend.toffee.Product;
import com.toffeestory.backend.toffee.ProductRepository;
import com.toffeestory.backend.toffee.Topping;
import com.toffeestory.backend.toffee.ToppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ToppingRepository toppingRepository;

    /*-------------------------------
       -select Post List
     -------------------------------*/
    @GetMapping(path = "/post")
    public List<Post> initPage() {
        return postRepository.findAll();
    }

    /*-------------------------------
       - select Post
     -------------------------------*/
    @GetMapping(path = "/{postNo}")
    public Post selectPost(@PathVariable("postNo") Integer postNo) {
        Post post = postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
        List<Product> products = new ArrayList<>();
        List<Topping> toppings = new ArrayList<>();

        List<PostToppingDtl> postToppingDtls = postToppingDtlRepository.findByPostNo(postNo);

        for (int i = 0; i < postToppingDtls.size(); i++) {
            products.add(productRepository.findByProductNo(postToppingDtls.get(i).getProductNo()).orElseThrow(() -> new RuntimeException()));
            toppings.add(toppingRepository.findByToppingNo(postToppingDtls.get(i).getToppingNo()).orElseThrow(() -> new RuntimeException()));
        }

        post.setProducts(products);
        post.setToppings(toppings);

        return post;
    }

    /*-------------------------------
      -select Related Post List
    -------------------------------*/
    @GetMapping("/{postNo}/relatedPost")
    public List<Post> relatedPostList(@PathVariable("postNo") Integer postNo) {
        List<Post> posts = new ArrayList<>();

        //해당 게시글이 가지고 있는 토핑, 상품 조회
        List<PostToppingDtl> postToppingDtls = postToppingDtlRepository.findByPostNo(postNo);

        for (int i = 0; i < postToppingDtls.size(); i++) {
            int productNo = postToppingDtls.get(i).getProductNo();
            int toppingNo = postToppingDtls.get(i).getToppingNo();

            // 토핑, 상품을 가지고 있는 게시글 넘버 가져옴
            List<PostToppingDtl> setPostNo = postToppingDtlRepository.findByProductNoOrToppingNo(productNo, toppingNo);

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
    public List<Post> tagPostList(@PathVariable("tagName") Integer tagName) {
        List<Post> post = new ArrayList<>();
        List<Integer> postNo = postToppingDtlRepository.findPostNoByProductNoOrToppingNo(tagName);

        for (int i = 0; i < postNo.size(); i++) {
            post.add(postRepository.findByPostNo(postNo.get(i)).orElseThrow(() -> new RuntimeException()));
        }

        return post;
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
