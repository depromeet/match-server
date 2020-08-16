package com.depromeet.match.user.dto;

import java.util.List;

public final class UpdateUserRequest {
    private long id;
    private String userName;
    private String email;
    private String phone;

    private String jobGroup;
    private String birthday;
    private Integer yearExperience;

    private List<String> tags;

    public long getId() {
        return id;
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

    public String getJobGroup() {
        return jobGroup;
    }

    public String getBirthday() {
        return birthday;
    }

    public Integer getYearExperience() {
        return yearExperience;
    }

    public List<String> getTags() {
        return tags;
    }
}
