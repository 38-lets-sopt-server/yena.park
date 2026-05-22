package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public interface SuccessCode extends BaseCode {
    HttpStatus getHttpStatus();
}
