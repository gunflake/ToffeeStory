package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class ProductToppingMap implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    @Id
    @ManyToOne
    @JoinColumn(name = "toppingNo")
    private Topping topping;

    @Column
    private Integer defaultValue;

    @Column
    private Boolean checkFlag;
}