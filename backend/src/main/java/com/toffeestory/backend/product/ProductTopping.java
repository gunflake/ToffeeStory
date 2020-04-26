package com.toffeestory.backend.product;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductTopping {

    @Id
    private Integer seqNo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productNo")
    private Product product;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "toppingNo")
    private Topping topping;

    @ManyToOne
    @JoinColumn(name = "subToppingNo")
    private SubTopping subTopping;

    @ManyToOne
    @JoinColumn(name = "quantityCodeNo")
    private QuantityCode quantityCode;

    @Column
    private Integer value;

    @Column
    private Integer optionType;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    public ProductTopping() {
        this.useStateCode = ProductStatus.USE;
    }
}