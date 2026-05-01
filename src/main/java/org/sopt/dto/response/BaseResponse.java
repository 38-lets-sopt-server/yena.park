package org.sopt.dto.response;

import org.sopt.common.code.BaseCode;

public class BaseResponse<T> {

    private final boolean success;
    private final String code;
    private final String message;
    private final T result;

    public BaseResponse(boolean success, String code, String message, T result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public static <T> BaseResponse<T> of(BaseCode code, T result) {
        return new BaseResponse<>(
                true,
                code.getCode(),
                code.getMessage(),
                result
        );
    }

    public static <T> BaseResponse<T> onFailure(BaseCode code, T result) {
        return new BaseResponse<>(
                false,
                code.getCode(),
                code.getMessage(),
                result
        );
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getResult() {
        return result;
    }
}