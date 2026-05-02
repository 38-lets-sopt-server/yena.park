package org.sopt.exception;

import org.sopt.common.code.ErrorStatus;
import org.sopt.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Void>> handlePostNotFound(BusinessException e) {
        ErrorStatus status = e.getErrorStatus();

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.onFailure(status, null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        ErrorStatus status = ErrorStatus.INVALID_INPUT;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.onFailure(status, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>> handleException(Exception e) {
        ErrorStatus status = ErrorStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(BaseResponse.onFailure(status, null));
    }
}