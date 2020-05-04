package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeverageRepository extends JpaRepository<Beverage, Integer> {
    List<BeverageSummary> findAllByUseStateCode(ProductStatus productStatus);
}
