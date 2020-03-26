package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class QuantityCode {
    @Id
    @Column(name = "quantityCode")
    private Integer quantityCode;

    @Column
    @GeneratedValue
    private Integer seqNo;

    @Column
    private String quantityName;

    @Column
    private String quantityType;

    @Column
    private Byte useStateCode;
}