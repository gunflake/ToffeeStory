package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeverageCategoryRepository extends JpaRepository<BeverageCategory, Integer> {

}
