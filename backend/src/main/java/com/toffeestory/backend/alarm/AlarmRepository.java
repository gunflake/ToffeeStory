package com.toffeestory.backend.alarm;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    Optional<Alarm> findByTargetAccountAndTargetPostAndWatchaAccountNo(Account targetAccount, Post targetPost, Integer watchaAccountNo);
    List<Alarm> findAllByTargetAccountAndStatus(Account targetAccount, AlarmStatus alarmStatus);
}
