package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestPostRepository extends JpaRepository<InterestPost, Integer> {

    Optional<InterestPost> findByPostNoAndAccountNo(Integer postNo, Integer accountNo);

    //interest list of account
    List<InterestPost> findByAccountNoAndLikeState(Integer accountNo, InterestPost.UseType likeState);
    List<InterestPost> findByAccountNoAndBookmarkState(Integer accountNo, InterestPost.UseType bookmarkState);
}
