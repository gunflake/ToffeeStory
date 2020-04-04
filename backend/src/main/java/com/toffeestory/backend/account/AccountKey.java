package com.toffeestory.backend.account;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
public class AccountKey {
    @Id
    @GeneratedValue
    private Integer seqNo;

    @NotNull
    @Column
    private Integer accountNo;

    @NotNull
    @Column(unique = true)
    private String token;

    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date regDate;

    @Temporal(TemporalType.DATE)
    private Date updDate;

    @NotNull
    private Byte useState;

    public AccountKey() {
        this.useState = 1;
    }
}

