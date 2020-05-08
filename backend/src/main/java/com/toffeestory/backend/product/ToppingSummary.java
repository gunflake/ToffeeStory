package com.toffeestory.backend.product;

import java.util.List;

public interface ToppingSummary {
    Integer getToppingNo();
    ToppingCategorySummary getToppingCategory();
    String getToppingName();
    Integer getToppingPrice();
    QuantityTypeSummary getQuantityType();
    List<SubToppingSummary> getSubToppingList();
}
