package com.toffeestory.backend.product;

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

    @JsonManagedReference(value = "quantityCode")
    @OneToMany(mappedBy = "quantityCode")
    private List<BeverageTopping> beverageToppingList = new ArrayList<>();

    @OneToMany(mappedBy = "quantityCode")
    @JsonManagedReference(value = "quantityCode")
    private List<PostDtl> postDtlList = new ArrayList<>();

    public QuantityCode() {
        this.useStateCode = ProductStatus.USE;
    }
}
