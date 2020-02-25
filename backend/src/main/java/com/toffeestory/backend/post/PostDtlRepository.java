package com.toffeestory.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDtlRepository extends JpaRepository<PostDtl, Integer> {
    List<PostDtl> findByPostNo(Integer postNo);
    List<PostDtl> findByTagName(String tagName);
}
