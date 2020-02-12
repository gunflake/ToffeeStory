package com.toffeestory.backend.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostToppingDtlRepository extends JpaRepository<PostToppingDtl, Integer> {
    List<PostToppingDtl> findByPostNo(Integer postNo);
    List<PostToppingDtl> findByProductNoOrToppingNo(Integer productNo, Integer toppingNo);
    List<Integer> findPostNoByProductNoOrToppingNo(Integer productNo);
}
