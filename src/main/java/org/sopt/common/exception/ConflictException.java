package org.sopt.common.exception;
import org.sopt.common.code.CommonErrorStatus;

public class ConflictException extends BusinessException {

    public ConflictException(CommonErrorStatus commonErrorStatus) {
        super(commonErrorStatus);
    }
}