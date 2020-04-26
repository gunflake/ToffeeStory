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
public class ProductCategory {
    @Id
    private Integer productCategoryNo; // PK, Auto Increase

    @NotNull
    @Column(length = 20)
    private String productCategoryName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "productCategory")
    @JsonBackReference
    private List<Product> productList = new ArrayList<>();

    public ProductCategory() {
        this.useStateCode = ProductStatus.USE;
    }
}
