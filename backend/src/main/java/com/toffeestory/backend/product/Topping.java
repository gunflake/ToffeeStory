package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Topping {
    @Id
    @GeneratedValue
    private Integer toppingNo; // PK, Auto Increase

    @ManyToOne
    @JoinColumn(name = "toppingCategoryNo")
    private ToppingCategory toppingCategory;

    @Column(length = 20)
    private String toppingName;

    @Column
    private boolean subFlag;

    @Column(length = 3)
    private Integer subToppingGroupNo;

    @Column
    private Integer toppingPrice;

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
