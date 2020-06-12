package com.toffeestory.backend.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toffeestory.backend.product.QuantityCode;
import com.toffeestory.backend.product.SubTopping;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@JsonIgnoreProperties("post")
public class PostDtl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seqNo;

    @ManyToOne
    @JoinColumn(name = "postNo")
    @JsonBackReference(value = "post")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToffeeType flag;  //0: product, 1: topping

    @Column(nullable = false)
    private Integer toffeeKey;

    @ManyToOne
    @JoinColumn(name = "subToppingNo")
    @JsonBackReference(value = "subTopping")
    private SubTopping subTopping;

    @ManyToOne
    @JoinColumn(name = "quantityCodeNo")
    @JsonBackReference(value = "quantityCode")
    private QuantityCode quantityCode;

    @Column(length = 20)
    private Integer value;

    @Column(nullable = false, length = 20)
    private String tagName;

    public enum ToffeeType {
        PRODUCT, TOPPING;
    }
}
