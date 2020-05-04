package com.toffeestory.backend.product;

public interface ToppingSummary {
    Integer getToppingNo();
    ToppingCategorySummary getToppingCategory();
    String getToppingName();
    Integer getToppingPrice();
    QuantityTypeSummary getQuantityType();
}
