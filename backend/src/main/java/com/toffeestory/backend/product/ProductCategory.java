package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class ProductCategory {
    @Id
    @GeneratedValue
    private Integer productCategoryNo; // PK, Auto Increase

    @Column(length = 20)
    private String productCategoryName;

    @Column
    private Boolean useStateCode;

    @Column
    private Date regDate;

    @OneToMany(mappedBy = "productCategory")
    private List<Product> productList = new ArrayList<>();
}
