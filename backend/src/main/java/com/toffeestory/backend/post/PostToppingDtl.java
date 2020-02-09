package com.toffeestory.backend.post;

import javax.persistence.*;

@Entity
public class PostToppingDtl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seqNo;

    @Column(nullable = false)
    private Integer postNo;

    @Column(nullable = false)
    private Integer productNo;

    @Column(nullable = false)
    private Integer toppingNo;

    @Column(nullable = false, length = 20)
    private String value;

    @Column(nullable = false)
    private Byte useState;

    public Integer getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Integer seqNo) {
        this.seqNo = seqNo;
    }

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public Integer getToppingNo() {
        return toppingNo;
    }

    public void setToppingNo(Integer toppingNo) {
        this.toppingNo = toppingNo;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PostToppingDtl() {
        this.useState = 1;
    }
}
