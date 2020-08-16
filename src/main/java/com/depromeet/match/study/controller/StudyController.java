package com.depromeet.match.study.controller;

import com.depromeet.match.study.dto.CreateStudyRequest;
import com.depromeet.match.study.service.StudyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

}
