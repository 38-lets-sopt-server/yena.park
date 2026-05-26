package org.sopt.domain.user.exception;

import org.sopt.common.code.ErrorCode;
import org.springframework.http.HttpStatus;

public enum UserErrorStatus implements ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    UserErrorStatus(HttpStatus httpStatus, String code, String message) {
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