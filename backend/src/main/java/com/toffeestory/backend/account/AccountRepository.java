package com.toffeestory.backend.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    @Transactional
    Optional<Account> findByAccountId(String accountId);
    Optional<Account> findByEmail(String email);
    Optional<Account> findByAccountNo(Integer accountNo);
}
