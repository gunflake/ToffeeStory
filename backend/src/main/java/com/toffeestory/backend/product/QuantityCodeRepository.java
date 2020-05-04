package com.toffeestory.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuantityCodeRepository extends JpaRepository<QuantityCode, Integer> {
    List<QuantityCodeSummary> findAllByUseStateCode(ProductStatus productStatus);
}
