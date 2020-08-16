package com.depromeet.match.user;

import com.depromeet.match.core.BaseEntity;
import com.depromeet.match.jobgroup.JobGroup;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {
    public static final User EMPTY = new User();
    @Id
    private long id; // 카카오 app user id를 pk 로 사용 한다.
    private String nickName;
    private String profileImageUrl;

    private String userName;
    private String email;
    private String phone;

    @OneToOne(cascade = CascadeType.DETACH)
    private JobGroup jobGroup;
    private String birthday;
    private Integer yearExperience;

    public long getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public JobGroup getJobGroup() {
        return jobGroup;
    }

    public String getBirthday() {
        return birthday;
    }

    public Integer getYearExperience() {
        return yearExperience;
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

    public void update(
            String userName,
            String email,
            String phone,
            JobGroup jobGroup,
            String birthday,
            Integer yearExperience
    ){
        this.userName = userName;
        this.email = email;
        this.phone = phone;
        this.jobGroup = jobGroup;
        this.birthday = birthday;
        this.yearExperience = yearExperience;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    public static final class UserBuilder {
        private long id; // 카카오 app user id를 pk 로 사용 한다.
        private String nickName;
        private String profileImageUrl;
        private String userName;
        private String email;
        private String phone;
        private JobGroup jobGroup;
        private String birthday;
        private Integer yearExperience;

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

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder jobGroup(JobGroup jobGroup) {
            this.jobGroup = jobGroup;
            return this;
        }

        public UserBuilder birthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public UserBuilder yearExperience(Integer yearExperience) {
            this.yearExperience = yearExperience;
            return this;
        }

        public User build() {
            User user = new User();
            user.profileImageUrl = this.profileImageUrl;
            user.email = this.email;
            user.nickName = this.nickName;
            user.yearExperience = this.yearExperience;
            user.birthday = this.birthday;
            user.id = this.id;
            user.phone = this.phone;
            user.userName = this.userName;
            user.jobGroup = this.jobGroup;
            return user;
        }
    }
}


