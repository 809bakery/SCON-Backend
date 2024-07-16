package com.example.scon.global.error.type;

import com.example.scon.global.error.ErrorCode;

public class ForbiddenException extends BusinessException {

    public ForbiddenException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public ForbiddenException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
