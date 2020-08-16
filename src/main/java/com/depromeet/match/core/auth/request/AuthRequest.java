package com.depromeet.match.core.auth.request;

public final class AuthRequest {
    private long id;
    private String nickName;
    private String profileImageUrl;

    private AuthRequest(){}

    public long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
