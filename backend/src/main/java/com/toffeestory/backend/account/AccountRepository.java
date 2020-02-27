package com.toffeestory.backend.account;

import com.toffeestory.backend.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.Transactional;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Transactional
    Optional<Account> findByAccountId(String accountId);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByAccountNo(Integer accountNo);
}
