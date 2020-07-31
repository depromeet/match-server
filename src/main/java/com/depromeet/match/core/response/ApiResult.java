package com.depromeet.match.core.response;

public final class ApiResult<T> {
    private final T response;

    public ApiResult(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }
}
