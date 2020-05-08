package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToppingCategoryRepository extends JpaRepository<ToppingCategory, Integer> {
    List<ToppingCategorySummary> findAllByUseStateCode(ProductStatus productStatus);
}
