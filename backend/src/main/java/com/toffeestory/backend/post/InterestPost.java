package com.toffeestory.backend.post;

import javax.persistence.*;

@Entity
public class InterestPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seqNo;

    @Column(nullable = false)
    private Integer postNo;

    @Column(nullable = false)
    private Integer accountNo;

    @Column(nullable = false)
    private Byte valueCode;

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public void setValueCode(Byte valueCode) {
        this.valueCode = valueCode;
    }
}
