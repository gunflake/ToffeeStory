package com.toffeestory.backend.alarm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.toffeestory.backend.account.Account;
import com.toffeestory.backend.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seqNo;

    @NotNull
    @ManyToOne
    @JsonBackReference(value = "targetAccount")
    @JoinColumn(name = "accountNo")
    private Account targetAccount;

    private Integer watchaAccountNo;

    @ManyToOne
    @JoinColumn(name = "postNo")
    private Post targetPost;

    @CreationTimestamp
    private LocalDateTime regDate;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private AlarmStatus status;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private AlarmType type;

}
