package com.toffeestory.backend.product;

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
public class Beverage {
    @Id
    private Integer beverageNo;

    @ManyToOne
    @JoinColumn(name = "beverageCategoryNo")
    private BeverageCategory beverageCategory;

    @Column(length = 20)
    @NotNull
    private String beverageName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @Column
    private Integer price;

    @OneToMany(mappedBy = "beverage")
    private List<BeverageTopping> beverageToppingList = new ArrayList<>();

    public Beverage() {
        this.useStateCode = ProductStatus.USE;
    }
}
