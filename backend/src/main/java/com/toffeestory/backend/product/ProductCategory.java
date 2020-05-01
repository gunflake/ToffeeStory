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
public class ProductCategory {
    @Id
    private Integer productCategoryNo;

    @NotNull
    @Column(length = 20)
    private String productCategoryName;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "productCategory")
    private List<Product> productList = new ArrayList<>();

    public ProductCategory() {
        this.useStateCode = ProductStatus.USE;
    }
}
