package com.toffeestory.backend.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private Integer accountNo;

    @Column(length = 30)
    private String accountId;

    @Column(length = 255)
    private String accountPwd;

    @Column(length = 30)
    private String accountName;

    @Column(length = 100)
    private String email;

    @Column(length = 30)
    private String instagram;

    @Column(length = 255)
    private String bio;

    @Column(length = 30)
    private String twitter;

    @Column(length = 255)
    private String profilePic;

    @Temporal(TemporalType.DATE)
    private Date regDate;

    @Temporal(TemporalType.DATE)
    private Date updDate;

    private Byte useStateCode;

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountPwd='" + accountPwd + '\'' +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
