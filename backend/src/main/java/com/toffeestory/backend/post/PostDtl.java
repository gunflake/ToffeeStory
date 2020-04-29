package com.toffeestory.backend.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @JsonBackReference
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToffeeType flag;  //0: product, 1: topping

    @Column(nullable = false)
    private Integer toffeeKey;

    @Column
    private Integer subToppingNo;

    @Column
    private Integer quantityNo;

    @Column(length = 20)
    private Integer value;

    @Column(nullable = false, length = 20)
    private String tagName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UseType useState;

    public PostDtl() {
        this.useState = UseType.USE;
    }

    public enum ToffeeType {
        PRODUCT, TOPPING;
    }
    public enum UseType {
        USE, UNUSED;
    }
}
