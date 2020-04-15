package com.toffeestory.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDtlRepository extends JpaRepository<PostDtl, Integer> {

    List<PostDtl> findByPost(Post post);
    List<PostDtl> findByTagName(String tagName);
}
