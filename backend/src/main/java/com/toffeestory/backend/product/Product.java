package com.toffeestory.backend.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productNo;

    @ManyToOne
    @JoinColumn(name = "productCategoryNo")
    private ProductCategory productCategory;

    @Column(length = 20)
    private String productName;

    @Column
    private Byte useSateCode;

    @Column
    private Date regDate;

    @Column
    private Integer price;

    @OneToMany(mappedBy = "product")
    private List<ProductTopping> productToppings = new ArrayList<>();

    @Transient
    private Integer productCategoryNo;
}