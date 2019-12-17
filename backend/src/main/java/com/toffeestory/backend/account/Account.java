package com.toffeestory.backend.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue
    private Integer accountNo;

    @NotNull
    @Column(length = 30)
    private String accountId;

    @NotNull
    @Size(min = 8)
    @Column(length = 255)
    private String accountPwd;

    @NotNull
    @Column(length = 30)
    private String accountName;

    @NotNull
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

    public String toStringCreateInfo(){
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", accountPwd='" + accountPwd + '\'' +
                ", accountName='" + accountName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
