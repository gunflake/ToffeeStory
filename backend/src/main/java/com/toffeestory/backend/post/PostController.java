package com.toffeestory.backend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    PostRepository postRepository;

    /*-------------------------------
       - Post List
     -------------------------------*/
    @GetMapping("")
    public List<Post> initPage() {
        return postRepository.findAll();
    }

    /*-------------------------------
       - 게시글
     -------------------------------*/
    @GetMapping("/{postNo}")
    public Post selectPost(@PathVariable("postNo") Integer postNo) {
        return postRepository.findByPostNo(postNo).orElseThrow(() -> new RuntimeException());
    }
}
