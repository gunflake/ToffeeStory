package com.toffeestory.backend.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class QuantityType {
    @Id
    private Integer quantityTypeNo;

    @NotNull
    @Column(length = 30)
    private String content;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "quantityType")
    @JsonBackReference
    private List<Topping> toppingList = new ArrayList<>();

    public QuantityType() {
        this.useStateCode = ProductStatus.USE;
    }
}
