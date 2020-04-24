package com.toffeestory.backend.account;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @Enumerated(EnumType.ORDINAL)
    private AccountKeyStatus keyStatus;

    public AccountKey() {
        this.keyStatus = AccountKeyStatus.UNUSED;
    }
}
