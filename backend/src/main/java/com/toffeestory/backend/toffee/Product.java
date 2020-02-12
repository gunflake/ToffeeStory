package com.toffeestory.backend.toffee;

import javax.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productNo;

    @Column(nullable = false, length = 20)
    private String productName;
}
