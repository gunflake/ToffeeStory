package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ProductTopping {
    @Id
    @GeneratedValue
    @Column(name = "productToppingId")
    private Integer productToppingId;

    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "toppingNo")
    private Topping topping;

    @Column
    private Integer subToppingNo;

    @Column
    private Byte quantityCode;

    @Column
    private Integer value;

    @Column
    private Integer optionType;
}