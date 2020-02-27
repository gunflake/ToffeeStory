package com.toffeestory.backend.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toffeestory.backend.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.annotations.*;

@Getter
@Setter
@Entity
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
    private Integer likeCnt;

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
    private List<String> tags;

    public void setAccount(Account account) {
        if(this.account != null){
            this.account.getPosts().remove(this);
        }
        this.account = account;
        this.account.getPosts().add(this);
    }

    // Post 생성시 likeCount, UserStateCode 기본값 세팅
    public Post() {
		this.tags = new ArrayList<>();
        this.tags.add("");
        this.likeCnt = 0;
        this.useStateCode = 1;
    }
}
