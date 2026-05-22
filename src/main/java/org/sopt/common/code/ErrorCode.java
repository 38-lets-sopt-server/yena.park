package org.sopt.common.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode extends BaseCode {
    HttpStatus getHttpStatus();
}