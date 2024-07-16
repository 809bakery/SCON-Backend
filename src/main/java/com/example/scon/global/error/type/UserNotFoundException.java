package com.example.scon.global.error.type;

import com.example.scon.global.error.ErrorCode;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public UserNotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public UserNotFoundException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
