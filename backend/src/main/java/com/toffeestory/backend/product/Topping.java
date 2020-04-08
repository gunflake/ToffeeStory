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
    private Boolean useStateCode;

    @OneToMany(mappedBy = "topping")
    private List<SubTopping> subToppingList = new ArrayList<>();

    @OneToMany(mappedBy = "topping")
    private List<ProductToppingMap> productToppingMapList = new ArrayList<>();
}