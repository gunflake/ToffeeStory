package com.toffeestory.backend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    InterestPostRepository interestPostRepository;

    public void updateInterest(Integer postNo, Integer accountNo, Byte valueCode, Byte useFlag) {
        InterestPost interestPost;

        if(useFlag == 0) {
            if(interestPostRepository.findByPostNoAndAccountNoAndValueCode(postNo, accountNo, valueCode).isPresent()) {
                interestPost = interestPostRepository.findByPostNoAndAccountNoAndValueCode(postNo, accountNo, valueCode).orElseThrow(() -> new RuntimeException());
                interestPost.setUseFlag((byte) 1);
            } else {
                interestPost = new InterestPost();
                interestPost.setPostNo(postNo);
                interestPost.setAccountNo(accountNo);
                interestPost.setValueCode(valueCode);
            }
        } else {
            interestPost = interestPostRepository.findByPostNoAndAccountNoAndValueCode(postNo, accountNo, valueCode).orElseThrow(() -> new RuntimeException());
            interestPost.setUseFlag((byte) 0);
        }

        interestPostRepository.save(interestPost);
    }
}