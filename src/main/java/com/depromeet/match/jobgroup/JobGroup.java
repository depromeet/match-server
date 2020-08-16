package com.depromeet.match.jobgroup;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "job_group", indexes = @Index(columnList = "name", unique = true))
public class JobGroup {
    @Id
    private long id;

    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
