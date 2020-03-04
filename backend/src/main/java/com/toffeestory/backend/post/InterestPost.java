package com.toffeestory.backend.post;

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

    @Column(nullable = false)
    private Integer postNo;

    @Column(nullable = false)
    private Integer accountNo;

    @Column(nullable = false)
    private Byte valueCode; // 0: like, 1: bookmark

    @Column(nullable = false)
    private Byte useFlag;   // 1: use

    public InterestPost() {
        this.useFlag = 1;
    }
}
