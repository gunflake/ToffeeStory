package com.toffeestory.backend.product;

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
public class BeverageCategory {
    @Id
    private Integer beverageCategoryNo; // PK, Auto Increase

    @NotNull
    @Column(length = 20)
    private String beverageCategoryName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "beverageCategory")
    @JsonManagedReference(value = "beverageCategory")
    private List<Beverage> beverageList = new ArrayList<>();

    public BeverageCategory() {
        this.useStateCode = ProductStatus.USE;
    }
}
