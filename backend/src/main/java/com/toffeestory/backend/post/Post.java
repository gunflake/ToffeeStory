package com.toffeestory.backend.post;

import com.toffeestory.backend.account.Account;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postNo;

    @ManyToOne
    @JoinColumn(name = "AccountNo")
    private Account AccountNo;

    @Column(nullable = false)
    private String postPic;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private Integer likeCount;

    @Column(nullable = false)
    private Short price;

    @Column(nullable = false)
    private Timestamp regDate;

    @Column
    private Timestamp updDate;

    @Column
    private Boolean useStateCode;
}
