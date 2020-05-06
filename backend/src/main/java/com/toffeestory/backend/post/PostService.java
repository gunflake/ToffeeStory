package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.exception.NotFoundPostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    InterestPostRepository interestPostRepository;

    public void updateInterest(Post post, Account account, Byte valueCode, Boolean useState) {
        InterestPost interestPost;

        InterestPost.UseType useType = InterestPost.UseType.UNINTERESTED;

        if(!useState) useType = InterestPost.UseType.INTEREST;

        //해당 정보가 있으면 찾아서 상태 값 변경
        if(interestPostRepository.findAllByPostAndAccount(post, account).isPresent()) {
            interestPost = interestPostRepository.findAllByPostAndAccount(post, account).orElseThrow(() -> new NotFoundPostException());

            if(valueCode == 0) {
                interestPost.setLikeState(useType);
            } else {
                interestPost.setBookmarkState(useType);
            }
        } else {
            interestPost = new InterestPost();

            interestPost.setPost(post);
            interestPost.setAccount(account);

            if(valueCode == 0) {
                interestPost.setLikeState(useType);
            } else {
                interestPost.setBookmarkState(useType);
            }
        }

        interestPostRepository.save(interestPost);
    }
}