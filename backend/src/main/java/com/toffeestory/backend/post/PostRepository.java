package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//기본적인 CRUD Operation
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUseStateCode(Post.UseType useCode);

    @Query("select distinct post from PostDtl where tagName like concat('%',:keyword,'%')")
    List<Post> findAllSearchKeywordPost(String keyword);

    List<Post> findAllByAccountAndUseStateCode(Account account, Post.UseType useCode);

}
