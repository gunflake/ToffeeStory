package com.toffeestory.backend.post;

import com.toffeestory.backend.exception.InvalidPostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private static final Logger LOG = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/enroll")
    public String enrollPost(@RequestBody @Valid Post post, BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors()) {
            List<ObjectError> errorLists = bindingResult.getAllErrors();

            for (ObjectError error : errorLists) {
                LOG.error(error.toString());
            }
            throw new InvalidPostException("Post 형식이 올바르지 않습니다.");
        }else{
            @Valid Post save = postRepository.save(post);
            return "Sucess";
        }
    }

}
