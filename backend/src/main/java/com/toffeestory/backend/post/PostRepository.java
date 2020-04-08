package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//기본적인 CRUD Operation
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Transactional
    Optional<Post> findByPostNo(Integer postNo);
    // Best
    List<Post> findAllByOrderByScoreDesc();
    // Hot
    List<Post> findAllByOrderByLikeCntDesc();
    List<Post> findAllByAccount(Account account);
}
