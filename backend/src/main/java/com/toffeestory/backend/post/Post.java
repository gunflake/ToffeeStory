package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
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
    private List<String> tags;

    public void setAccount(Account account) {
        this.account = account;

        if(!account.getPosts().contains(this)) {
            account.getPosts().add(this);
        }
    }

    // Post 생성시 likeCount, UserStateCode 기본값 세팅
    public Post() {
        this.tags = new ArrayList<>();
        this.tags.add("");
        this.likeCnt = 0;
        this.useStateCode = 1;
    }
}
