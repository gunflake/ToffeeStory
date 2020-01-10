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
    @GeneratedValue
    private Integer toppingCategoryNo; // PK, Auto Increase

    @Column(length = 20)
    private String toppingCategoryName;

    @OneToMany(mappedBy = "toppingCategory")
    private List<Topping> toppingList = new ArrayList<>();
}
