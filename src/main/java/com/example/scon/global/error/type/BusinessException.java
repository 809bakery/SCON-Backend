package com.example.scon.global.error.type;

import com.example.scon.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class BusinessException extends SconException {

    public BusinessException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}