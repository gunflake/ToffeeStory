package com.toffeestory.backend.product;

public interface BeverageToppingSummary {
    Integer getSeqNo();
    ToppingSummary getTopping();
    SubToppingSummary getSubTopping();
    QuantityCodeSummary getQuantityCode();
    Integer getToppingValue();
    Integer getOptionType();
}
