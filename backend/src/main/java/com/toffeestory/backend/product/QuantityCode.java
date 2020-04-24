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
public class QuantityCode {
    @Id
    private Integer quantityCodeNo;

    @NotNull
    @Column(length = 30)
    private String quantityName;

    @NotNull
    @Column(length = 30)
    private String quantityType;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    public QuantityCode() {
        this.useStateCode = ProductStatus.USE;
    }
}
