package com.toffeestory.backend.toffee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ToppingRepository extends JpaRepository<Topping, Integer> {
    Optional<Topping> findByToppingNo(Integer toppingNo);
}
