package org.sopt.common.exception;

import org.sopt.common.code.CommonErrorStatus;

public class NotFoundException extends BusinessException {

    public NotFoundException(CommonErrorStatus commonErrorStatus) {
        super(commonErrorStatus);
    }
}