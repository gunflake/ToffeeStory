package com.toffeestory.backend.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Topping {
    @Id
    private Integer toppingNo; // PK, Auto Increase

    @NotNull
    @ManyToOne
    @JsonBackReference(value = "toppingCategory")
    @JoinColumn(name = "toppingCategoryNo")
    private ToppingCategory toppingCategory;

    @NotNull
    @Column(length = 20)
    private String toppingName;

    @ManyToOne
    @JsonBackReference(value = "quantityType")
    @JoinColumn(name = "quantityTypeNo")
    private QuantityType quantityType;

    @Column
    private Integer toppingPrice;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @JsonManagedReference(value = "topping")
    @OneToMany(mappedBy = "topping")
    private List<SubTopping> subToppingList = new ArrayList<>();

    @JsonManagedReference(value = "topping")
    @OneToMany(mappedBy = "topping")
    private List<BeverageTopping> beverageToppingList = new ArrayList<>();

    public Topping() {
        this.useStateCode = ProductStatus.USE;
    }

}
