package com.toffeestory.backend.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toffeestory.backend.account.Account;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.File;
import java.util.Date;

@Entity
@Getter
@Setter
@JsonIgnoreProperties("account")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postNo;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "accountNo")
    private Account account;

    @Column(nullable = false)
    private String postPic;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private Short price;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date regDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;

    @Column
    private Byte useStateCode;

    @Transient
    private File file;

    public void setAccount(Account account) {
        if(this.account != null){
            this.account.getPost().remove(this);
        }
        this.account = account;
        this.account.getPost().add(this);
    }

    // Post 생성시 likeCount, UserStateCode 기본값 세팅
    public Post() {
        this.likeCount = 0;
        this.useStateCode = 1;
    }
}
