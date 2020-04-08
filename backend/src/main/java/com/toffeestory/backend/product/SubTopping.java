package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SubTopping {
    @Id
    @Column(name = "subToppingNo")
    @GeneratedValue
    private Integer subToppingNo;

    @ManyToOne
    @JoinColumn(name = "toppingNo")
    private Topping topping;

    @Column(length = 20)
    private String subToppingName;

    @Column
    private Integer displayOrder;

    @Column
    private Byte useStateCode;
}
