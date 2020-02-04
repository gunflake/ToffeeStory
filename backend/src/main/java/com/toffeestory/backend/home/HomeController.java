package com.toffeestory.backend.home;

import com.toffeestory.backend.post.Post;
import com.toffeestory.backend.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    @Autowired
    PostRepository postRepository;

    /*-------------------------------
       - Post List
     -------------------------------*/
    public List<Post> initPage() {
        return postRepository.findAll();
    }
}
