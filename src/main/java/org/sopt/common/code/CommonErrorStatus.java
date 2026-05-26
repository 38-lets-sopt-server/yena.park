package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public enum CommonErrorStatus implements ErrorCode {

    INVALID_INPUT(HttpStatus.BAD_REQUEST, "COMMON_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON_500", "서버 내부 오류");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    CommonErrorStatus(HttpStatus httpStatus, String code, String message) {
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