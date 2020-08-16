package com.depromeet.match.jobgroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobGroupRepository extends JpaRepository<JobGroup, Long> {

    JobGroup findByName(String name);
}
