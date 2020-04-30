package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestPostRepository extends JpaRepository<InterestPost, Integer> {

    Optional<InterestPost> findAllByPostAndAccount(Post post, Account accountNo);

    //interest list of account
    List<InterestPost> findAllByAccountAndLikeState(Account account, InterestPost.UseType likeState);
    List<InterestPost> findAllByAccountAndBookmarkState(Account account, InterestPost.UseType bookmarkState);
}
