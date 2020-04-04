package com.toffeestory.backend.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    InterestPostRepository interestPostRepository;

    public void updateInterest(Integer postNo, Integer accountNo, Byte valueCode, Boolean useState) {
        InterestPost interestPost;

        //해당 정보가 있으면 찾아서 상태 값 변경
        if(interestPostRepository.findByPostNoAndAccountNo(postNo, accountNo).isPresent()) {
            interestPost = interestPostRepository.findByPostNoAndAccountNo(postNo, accountNo).orElseThrow(() -> new RuntimeException());

            if(valueCode == 0) {
                interestPost.setLikeState(!useState);
            } else {
                interestPost.setBookmarkState(!useState);
            }
        } else {
            interestPost = new InterestPost();

            interestPost.setPostNo(postNo);
            interestPost.setAccountNo(accountNo);

            if(valueCode == 0) {
                interestPost.setLikeState(!useState);
            } else {
                interestPost.setBookmarkState(!useState);
            }
        }

        interestPostRepository.save(interestPost);
    }
}