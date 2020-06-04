package com.toffeestory.backend.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@JsonIgnoreProperties("account")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postNo;

    @ManyToOne
    @JsonBackReference(value = "account")
    @JoinColumn(name = "accountNo")
    private Account account;

    @Column(nullable = false)
    private String src;

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

    @Enumerated(EnumType.ORDINAL)
    @Column
    private UseType useStateCode;

	@Transient
    private List<String> tags;

	@OneToMany(mappedBy = "post")
    @JsonManagedReference(value = "post")
    private List<PostDtl> postDtlList = new ArrayList<>();

	@OneToMany(mappedBy = "post")
    @JsonManagedReference(value = "post")
    private List<InterestPost> interestPostList  = new ArrayList<>();

	@Transient
    private String compressSrc;

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
        this.useStateCode = UseType.USE;
    }
    public enum UseType {
        USE, UNUSED;
    }

    public String getCompressSrc() {
        if(src == null)
            return null;
        else{
            int index = src.lastIndexOf('.');

            if(index == -1) {
                return null;
            }else{
                return src.substring(0, index).concat("-compress").concat(src.substring(index));
            }
        }
    }
}
