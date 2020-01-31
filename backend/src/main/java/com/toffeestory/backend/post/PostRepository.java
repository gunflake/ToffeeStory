package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByAccount(Account account);
    List<Post> findAllByAccount(Account account);
}
