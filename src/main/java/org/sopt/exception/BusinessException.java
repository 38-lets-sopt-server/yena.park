package org.sopt.exception;

import org.sopt.common.code.ErrorStatus;

public class BusinessException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public BusinessException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorStatus() {
        return errorStatus;
    }
}