package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ToppingCategory {
    @Id
    @Column(name = "toppingCategoryNo")
    @GeneratedValue
    private Integer toppingCategoryNo;

    @Column(length = 20)
    private String toppingCategoryName;

    @Column
    private Byte useStateCode;

    @OneToMany(mappedBy = "toppingCategory")
    private List<Topping> toppings = new ArrayList<>();
}