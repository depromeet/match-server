package com.depromeet.match.error;

import com.depromeet.match.core.response.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MatchAdviser {

    @ExceptionHandler(MatchServiceException.class)
    public ResponseEntity<ApiError> serviceError(MatchServiceException e) {
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage()));
    }
}
