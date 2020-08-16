package com.depromeet.match.study.service;

import com.depromeet.match.study.*;
import com.depromeet.match.study.dto.CreateStudyRequest;
import com.depromeet.match.study.repository.StudyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StudyService {

    private final StudyRepository studyRepository;

    public StudyService(StudyRepository studyRepository){
        this.studyRepository = studyRepository;
    }

    @Transactional
    public Long create(CreateStudyRequest request) {
        Spot spot = new Spot();
        spot.setAddr1(request.getAddr1());
        spot.setAddr2(request.getAddr2());
        spot.setLatitude(request.getLatitude());
        spot.setLongitude(request.getLongitude());
        spot.setZipCode(request.getZipCode());

        Duration duration = new Duration();
        duration.setStart(LocalDate.parse(request.getStart(), DateTimeFormatter.ISO_DATE));
        duration.setEnd(LocalDate.parse(request.getEnd(), DateTimeFormatter.ISO_DATE));

        Study study = Study.StudyBuilder.aStudy()
                .studyName(request.getName())
                .studyConductWay(StudyConductWay.valueOf(request.getConductWay()))
                .studySubject(StudySubject.valueOf(request.getSubject()))
                .spot(spot)
                .duration(duration)
                .build();

        study = studyRepository.save(study);

        return study.getId();
    }

    public List<Study> findByLatitude(
            double latitude,
            double longitude,
            int offset,
            int limit
    ) {
        return studyRepository.findByLatitude(latitude, longitude, offset, limit);
    }
}
