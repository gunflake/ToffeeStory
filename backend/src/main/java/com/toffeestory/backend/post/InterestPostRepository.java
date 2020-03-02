package com.toffeestory.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterestPostRepository extends JpaRepository<InterestPost, Integer> {

    Optional<InterestPost> findByPostNoAndAccountNoAndValueCode(Integer postNo, Integer accountNo, Byte valueCode);

    //select useFlag
    @Query(value = "SELECT IP.useFlag FROM InterestPost IP WHERE IP.postNo = :postNo AND IP.accountNo = :accountNo AND IP.valueCode = :valueCode", nativeQuery = true)
    Byte selectUseFlag(@Param("postNo") Integer postNo, @Param("accountNo") Integer accountNo, @Param("valueCode") Byte valueCode);

    //interest list of account
    List<InterestPost> findByAccountNoAndValueCode(Integer accountNo, Byte valueCode);
}
