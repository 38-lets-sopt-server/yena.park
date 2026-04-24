package org.sopt.exception;

import org.sopt.common.code.ErrorStatus;
import org.sopt.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePostNotFound(PostNotFoundException e) {
        ErrorStatus status = ErrorStatus.POST_NOT_FOUND;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status, null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException e) {
        ErrorStatus status = ErrorStatus.INVALID_INPUT;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        ErrorStatus status = ErrorStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status.getHttpStatus())
                .body(ApiResponse.onFailure(status, null));
    }
}