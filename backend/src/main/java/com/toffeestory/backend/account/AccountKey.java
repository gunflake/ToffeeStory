package com.toffeestory.backend.account;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @Column(unique = true, length = 100)
    private String token;

    @CreationTimestamp
    private LocalDateTime regDate;

    private LocalDateTime updDate;

    @NotNull
    private Byte useState;

    public AccountKey() {
        this.useState = 1;
    }
}

