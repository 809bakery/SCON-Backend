package com.example.scon.global.error.type;

import com.example.scon.global.error.ErrorCode;

public class BadRequestException extends BusinessException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BadRequestException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
