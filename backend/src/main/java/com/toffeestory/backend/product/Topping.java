package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Topping {
    @Id
    @Column(name = "toppingNo")
    @GeneratedValue
    private Integer toppingNo;

    @ManyToOne
    @JoinColumn(name = "toppingCategoryNo")
    private ToppingCategory toppingCategory;

    @Column(length = 20)
    private String toppingName;

    @Column
    private Integer toppingPrice;

    @Column
    private Integer quantityType;

    @Column
    private Byte useStateCode;

    // 토핑 초기값 관련 필드
    @Transient
    private Byte quantityCode;

    @Transient
    private Integer value;

    @Transient
    private Integer optionType;
}