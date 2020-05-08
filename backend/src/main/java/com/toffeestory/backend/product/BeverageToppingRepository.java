package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeverageToppingRepository extends JpaRepository<BeverageTopping, Integer> {
    List<BeverageToppingSummary> findAllByBeverage(Beverage beverage);
}
