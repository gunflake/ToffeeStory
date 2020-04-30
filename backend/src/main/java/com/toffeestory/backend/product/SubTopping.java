package com.toffeestory.backend.product;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toffeestory.backend.post.PostDtl;
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
public class SubTopping {

    @Id
    private Integer subToppingNo; // PK, Auto Increase

    @NotNull
    @ManyToOne
    @JoinColumn(name = "toppingNo")
    private Topping topping;

    @NotNull
    @Column(length = 20)
    private String subToppingName;

    @NotNull
    @Column
    private Integer displayOrder;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private ProductStatus useStateCode;

    @CreationTimestamp
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "subTopping")
    private List<ProductTopping> productToppingList = new ArrayList<>();

    @OneToMany(mappedBy = "subTopping")
    @JsonManagedReference(value = "subTopping")
    private List<PostDtl> postDtlList = new ArrayList<>();

    public SubTopping() {
        this.useStateCode = ProductStatus.USE;
    }
}
