package com.toffeestory.backend.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {
    private Post   post;
    private String accountId;
    private String accountPic;
    private Integer likeState;
    private Integer bookmarkState;

    public ResponsePost(Post post, String accountId, String accountPic, Integer likeState, Integer bookmarkState) {
        this.post          = post;
        this.accountId     = accountId;
        this.accountPic    = accountPic;
        this.likeState     = likeState;
        this.bookmarkState = bookmarkState;
    }
}

