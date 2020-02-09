package com.toffeestory.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestPostRepository extends JpaRepository<InterestPost, Integer> {
    List<InterestPost> findByAccountNoAndValueCode(Integer accountNo, Byte valueCode);
}
