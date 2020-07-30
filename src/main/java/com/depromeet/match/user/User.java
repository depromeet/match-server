package com.depromeet.match.user;

public class User {
    private long id; // 카카오 app user id를 pk 로 사용한다.
    private String nickName;
    private String profileImageUrl;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public static final class UserBuilder {

        private long id; // 카카오 app user id를 pk 로 사용한다.
        private String nickName;
        private String profileImageUrl;

        private UserBuilder() {
        }

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder id(long id) {
            this.id = id;
            return this;
        }

        public UserBuilder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }

        public UserBuilder profileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
            return this;
        }

        public User build() {
            User user = new User();
            user.profileImageUrl = this.profileImageUrl;
            user.id = this.id;
            user.nickName = this.nickName;
            return user;
        }
    }
}


