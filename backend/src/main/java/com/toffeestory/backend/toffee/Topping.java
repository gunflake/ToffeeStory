package com.toffeestory.backend.toffee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Topping {
    @Id
    private Integer toppingNo;

    @Column(nullable = false, length = 20)
    private String toppingName;
}
