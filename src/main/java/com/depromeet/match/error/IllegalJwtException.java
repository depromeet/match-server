package com.depromeet.match.error;

public class IllegalJwtException extends RuntimeException {

    public IllegalJwtException() {
    }

    public IllegalJwtException(String message) {
        super(message);
    }
}
