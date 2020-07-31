package com.depromeet.match.core.jwt;

import java.util.StringJoiner;

public class JwtAuthentication {
    private final long id;
    private final String nickName;
    private final String profileImageUrl;

    public JwtAuthentication(long id, String nickName, String profileImageUrl) {
        this.id = id;
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
    }

    public long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JwtAuthentication.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("nickName='" + nickName + "'")
                .add("profileImageUrl='" + profileImageUrl + "'")
                .toString();
    }
}
