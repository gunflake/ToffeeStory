package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeverageCategoryRepository extends JpaRepository<BeverageCategory, Integer> {
    List<BeverageCategorySummary> findAllByUseStateCode(ProductStatus productStatus);
}
