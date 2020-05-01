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
public class ToppingCategory {
    @Id
    private Integer toppingCategoryNo; // PK, Auto Increase

    @NotNull
    @Column(length = 20)
    private String toppingCategoryName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "toppingCategory")
    @JsonManagedReference(value = "toppingCategory")
    private List<Topping> toppingList = new ArrayList<>();

    public ToppingCategory() {
        this.useStateCode = ProductStatus.USE;
    }
}
