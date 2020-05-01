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
public class Product {
    @Id
    private Integer productNo;

    @ManyToOne
    @JoinColumn(name = "productCategoryNo")
    private ProductCategory productCategory;

    @Column(length = 20)
    @NotNull
    private String productName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @Column
    private Integer price;

    @OneToMany(mappedBy = "product")
    private List<ProductTopping> productToppingList = new ArrayList<>();

    public Product() {
        this.useStateCode = ProductStatus.USE;
    }
}
