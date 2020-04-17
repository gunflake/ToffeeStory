package com.toffeestory.backend.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AccountKeyRepository extends JpaRepository<AccountKey, Integer> {
    Optional<AccountKey> findByTokenAndRegDateAfterAndKeyStatus(String token, LocalDateTime time, AccountKeyStatus keyStatus);
}
