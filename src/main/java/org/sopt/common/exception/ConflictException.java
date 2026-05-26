package org.sopt.common.exception;

import org.sopt.common.code.ErrorCode;

public class ConflictException extends BusinessException {

    public ConflictException(ErrorCode errorCode) {
        super(errorCode);
    }
}