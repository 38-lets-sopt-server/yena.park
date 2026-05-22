package org.sopt.domain.like.exception;

import org.sopt.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum LikeErrorStatus implements ErrorCode {

    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "LIKE_001", "이미 좋아요를 눌렀습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "LIKE_002", "좋아요가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    LikeErrorStatus(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}