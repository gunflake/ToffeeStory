package com.toffeestory.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InterestPostRepository extends JpaRepository<InterestPost, Integer> {

    Optional<InterestPost> findByPostNoAndAccountNo(Integer postNo, Integer accountNo);

    //interest list of account
    List<InterestPost> findByAccountNoAndLikeState(Integer accountNo, Boolean likeState);
    List<InterestPost> findByAccountNoAndBookmarkState(Integer accountNo, Boolean bookmarkState);
}
