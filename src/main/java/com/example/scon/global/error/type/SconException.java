package com.example.scon.global.error.type;

import com.example.scon.global.error.ErrorCode;
import lombok.Getter;

@Getter
public class SconException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public SconException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public SconException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

    public SconException(ErrorCode errorCode, String message, Throwable cause) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.initCause(cause);
    }
}
