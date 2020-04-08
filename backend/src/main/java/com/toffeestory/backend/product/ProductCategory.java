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
    @Column(name = "productCategoryNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productCategoryNo;

    @Column(length = 20)
    private String productCategoryName;

    @Column
    private Byte useStateCode;

    @Column
    private Date regDate;

    @OneToMany(mappedBy = "productCategory")
    private List<Product> products = new ArrayList<>();
}