package com.depromeet.match.study.repository;

import com.depromeet.match.study.Study;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudyRepository extends PagingAndSortingRepository<Study, Long> {
}
