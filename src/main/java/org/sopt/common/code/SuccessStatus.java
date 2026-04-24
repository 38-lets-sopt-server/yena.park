package org.sopt.common.code;

public enum SuccessStatus implements BaseCode {

    POST_CREATE_SUCCESS("POST_201", "게시글 등록 성공"),
    POST_READ_ALL_SUCCESS("POST_200", "게시글 전체 조회 성공"),
    POST_READ_SUCCESS("POST_200", "게시글 단건 조회 성공"),
    POST_UPDATE_SUCCESS("POST_200", "게시글 수정 성공"),
    POST_DELETE_SUCCESS("POST_200", "게시글 삭제 성공");

    private final String code;
    private final String message;

    SuccessStatus(String code, String message) {
        this.code = code;
        this.message = message;
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