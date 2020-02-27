package com.toffeestory.backend.post;

import javax.persistence.*;

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

    @Column(nullable = false, length = 20)
    private String tagName;

    @Column(length = 20)
    private String value;

    @Column(nullable = false)
    private Byte useState;

    public Integer getSeqNo() {
        return seqNo;
    }

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
    }

    public void setToffeeKey(Integer toffeeKey) {
        this.toffeeKey = toffeeKey;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getValue() {
        return value;
    }

    public String getTagName() {
        return tagName;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public PostDtl() {
        this.useState = 1;
    }
}
