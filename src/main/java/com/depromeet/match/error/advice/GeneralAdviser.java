package com.depromeet.match.error.advice;

import com.depromeet.match.core.response.ApiError;
import com.depromeet.match.error.IllegalJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralAdviser {

    @ExceptionHandler(IllegalJwtException.class)
    public ResponseEntity<ApiError> illegalJwtError(IllegalJwtException e) {
        return ResponseEntity.status(401).body(new ApiError(e.getMessage()));
    }
}
