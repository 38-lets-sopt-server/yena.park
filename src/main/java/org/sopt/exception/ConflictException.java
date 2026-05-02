package org.sopt.exception;
import org.sopt.common.code.ErrorStatus;

public class ConflictException extends BusinessException {

    public ConflictException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}