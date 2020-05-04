package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubToppingRepository extends JpaRepository<SubTopping, Integer> {
    List<SubToppingSummary> findAllByTopping(Topping topping);
}
