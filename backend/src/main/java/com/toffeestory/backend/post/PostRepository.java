package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//기본적인 CRUD Operation
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Transactional
    Optional<Post> findByPostNo(Integer postNo);

    // New
    List<Post> findAllByOrderByPostNoDesc();

    @Query("select p from Post p where p.postNo in(select distinct postNo from PostDtl where tagName like concat('%',:keyword,'%')) order by p.postNo desc ")
    List<Post> findAllSearchKeywordPostOrderByPostNoDesc(String keyword);

    // Best
    List<Post> findAllByOrderByScoreDesc();

    @Query("select p from Post p where p.postNo in(select distinct postNo from PostDtl where tagName like concat('%',:keyword,'%')) order by p.score desc ")
    List<Post> findAllSearchKeywordPostOrderByScoreDesc(String keyword);
    // Hot

    List<Post> findAllByOrderByLikeCntDesc();

    @Query("select p from Post p where p.postNo in(select distinct postNo from PostDtl where tagName like concat('%',:keyword,'%')) order by p.likeCnt desc ")
    List<Post> findAllSearchKeywordPostOrderByLikeCntDesc(String keyword);

    List<Post> findAllByAccount(Account account);
}
