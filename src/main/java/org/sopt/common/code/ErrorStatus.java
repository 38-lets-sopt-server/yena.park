package org.sopt.common.code;

public enum ErrorStatus implements BaseCode {

    POST_NOT_FOUND("POST_001", "게시글을 찾을 수 없습니다."),
    INVALID_INPUT("COMMON_001", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR("COMMON_500", "서버 내부 오류");

    private final String code;
    private final String message;

    ErrorStatus(String code, String message) {
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
