package com.toffeestory.backend.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponsePost {
    private Post   post;
    private String accountId;
    private String accountPic;
    private Byte   likeFlag;
    private Byte   bookmarkFlag;

    public ResponsePost(Post post, String accountId, String accountPic, Byte likeFlag, Byte bookmarkFlag) {
        this.post         = post;
        this.accountId    = accountId;
        this.accountPic   = accountPic;
        this.likeFlag     = likeFlag;
        this.bookmarkFlag = bookmarkFlag;
    }
}

