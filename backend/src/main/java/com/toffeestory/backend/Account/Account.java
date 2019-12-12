package com.toffeestory.backend.Account;

import com.toffeestory.backend.Post.Post;

import java.util.List;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountNo;

    @Column(length = 30, nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String accountPwd;

    @Column(length = 30, nullable = false)
    private String accountName;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 30)
    private String instagram;

    @Column
    private String bio;

    @Column(length = 30)
    private String twitter;

    @Column
    private String profilePic;

    @Column(nullable = false)
    private Timestamp regDate;

    @Column
    private Timestamp updDate;

    @Column(nullable = false)
    private Boolean useStateCode;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> posts;
}
