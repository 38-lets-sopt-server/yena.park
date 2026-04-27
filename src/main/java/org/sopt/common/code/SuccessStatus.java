package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public enum SuccessStatus implements BaseCode {

    POST_CREATE_SUCCESS(HttpStatus.CREATED, "POST_201", "게시글 등록 성공"),
    POST_READ_ALL_SUCCESS(HttpStatus.OK, "POST_200", "게시글 전체 조회 성공"),
    POST_READ_SUCCESS(HttpStatus.OK, "POST_200", "게시글 단건 조회 성공"),
    POST_UPDATE_SUCCESS(HttpStatus.OK, "POST_200", "게시글 수정 성공"),
    POST_DELETE_SUCCESS(HttpStatus.OK, "POST_200", "게시글 삭제 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    SuccessStatus(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

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