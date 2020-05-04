package com.toffeestory.backend.product;

public interface BeverageSummary {
    Integer getBeverageNo();
    BeverageCategorySummary getBeverageCategory();
    String getBeverageName();
    Integer getPrice();
}
