package com.depromeet.match.study.controller;

import com.depromeet.match.core.response.ApiResult;
import com.depromeet.match.study.Study;
import com.depromeet.match.study.dto.CreateStudyRequest;
import com.depromeet.match.study.service.StudyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/studies")
public class StudyController {
    private final StudyService studyService;

    public StudyController(StudyService studyService) {
        this.studyService = studyService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final CreateStudyRequest request
    ) throws URISyntaxException {
        Long id = studyService.create(request);
        return ResponseEntity.created(new URI("/studies/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<Study>>> findByLatitude(
            @RequestParam double latitude,
            @RequestParam int offset,
            @RequestParam int limit
    )
    {
        List<Study> byLatitude = studyService.findByLatitude(latitude, offset, limit);
        return ResponseEntity.ok(new ApiResult<>(byLatitude));
    }


}
