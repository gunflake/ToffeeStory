package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.toffee.Product;
import com.toffeestory.backend.toffee.Topping;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postNo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountNo")
    private Account account;

    @Column(nullable = false)
    private String postPic;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private Integer likeCnt;

    @Column(nullable = false)
    private Short price;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;

    @Column(nullable = false)
    private Byte useStateCode;

    @Transient
    private List<Product> products = new ArrayList<>();

    @Transient
    private List<Topping> toppings = new ArrayList<>();

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPosts().contains(this)) {
            account.getPosts().add(this);
        }
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public Integer getPostNo() {
        return postNo;
    }

    public void setPostNo(Integer postNo) {
        this.postNo = postNo;
    }

    public Account getAccount() {
        return account;
    }

    public String getPostPic() {
        return postPic;
    }

    public void setPostPic(String postPic) {
        this.postPic = postPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Integer likeCnt) {
        this.likeCnt = likeCnt;
    }

    public Short getPrice() {
        return price;
    }

    public void setPrice(Short price) {
        this.price = price;
    }

    // Post 생성시 likeCount, UserStateCode 기본값 세팅
    public Post() {
        this.likeCnt = 0;
        this.useStateCode = 1;
    }
}
