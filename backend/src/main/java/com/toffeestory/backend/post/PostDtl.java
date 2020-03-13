package com.toffeestory.backend.post;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PostDtl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seqNo;

    @Column(nullable = false)
    private Integer postNo;

    @Column(nullable = false)
    private Byte flag;

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

    @Column(nullable = false)
    private Byte useState;

    public PostDtl() {
        this.useState = 1;
    }
}
