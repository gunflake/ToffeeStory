package com.toffeestory.backend.account;

import com.toffeestory.backend.post.Post;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Getter
@Setter
public class Account implements UserDetails {
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
    @CreationTimestamp
    private Date regDate;

    @Temporal(TemporalType.DATE)
    private Date updDate;

    @Column
    private Byte useStateCode;

    @Column(length = 20)
    private String authority;

    @OneToMany(mappedBy = "account")
    private List<Post> post = new ArrayList<>();

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

    public Account() {
        this.authority = "ROLE_USER";
        this.useStateCode = 1;
    }

    // UserDetails 필수 구현부분
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add(authority);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }

    @Override
    public String getPassword() {
        return this.accountPwd;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
