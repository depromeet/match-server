package com.depromeet.match.error;

public class MatchServiceException extends RuntimeException {
    public MatchServiceException() {
    }

    public MatchServiceException(String message) {
        super(message);
    }
}
