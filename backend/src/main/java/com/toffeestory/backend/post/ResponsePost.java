package com.toffeestory.backend.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {
    private Post post;
    private String accountId;
    private String accountPic;

    public ResponsePost(Post post, String accountId, String accountPic) {
        this.post = post;
        this.accountId = accountId;
        this.accountPic = accountPic;
    }
}

