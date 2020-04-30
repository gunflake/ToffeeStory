package com.toffeestory.backend.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.toffeestory.backend.account.Account;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class InterestPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seqNo;

    @ManyToOne
    @JoinColumn(name = "postNo")
    @JsonBackReference(value = "post")
    private Post post;

    @ManyToOne
    @JsonBackReference(value = "account")
    @JoinColumn(name = "accountNo")
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column
    private UseType likeState;

    @Enumerated(EnumType.STRING)
    @Column
    private UseType bookmarkState;

    @Getter
    public enum UseType {
        UNINTERESTED, INTEREST;
    }

    public InterestPost() {
        this.likeState = UseType.UNINTERESTED;
        this.bookmarkState = UseType.UNINTERESTED;
    }
}
