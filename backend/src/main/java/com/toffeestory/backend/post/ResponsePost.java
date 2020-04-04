package com.toffeestory.backend.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {
    private Post   post;
    private String accountId;
    private String accountPic;
    private Boolean likeState;
    private Boolean bookmarkState;

    public ResponsePost(Post post, String accountId, String accountPic, Boolean likeState, Boolean bookmarkState) {
        this.post          = post;
        this.accountId     = accountId;
        this.accountPic    = accountPic;
        this.likeState     = likeState;
        this.bookmarkState = bookmarkState;
    }
}

