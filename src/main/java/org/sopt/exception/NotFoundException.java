package org.sopt.exception;

import org.sopt.common.code.ErrorStatus;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}