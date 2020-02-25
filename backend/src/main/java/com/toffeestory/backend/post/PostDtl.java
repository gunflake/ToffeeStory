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
    private Integer key;

    @Column(nullable = false, length = 20)
    private String tagName;

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
