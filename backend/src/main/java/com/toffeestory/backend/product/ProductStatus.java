package com.toffeestory.backend.product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProductStatus {

    USE(0, "Use"),
    UNUSED(1, "Unused");

    private final int value;
    private final String description;

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.description;
    }
}
