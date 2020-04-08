package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SubTopping {
    @Id
    @GeneratedValue
    private Integer subToppingNo; // PK, Auto Increase

    @Column(length = 3)
    private String subToppingGroupCode;

    @Column
    private Integer subToppingOrder;

    @Column(length = 20)
    private String subToppingName;

    @ManyToOne
    @JoinColumn(name = "toppingNo")
    private Topping topping;
}
