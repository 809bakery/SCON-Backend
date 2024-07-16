package com.example.scon.global.error.type;

import com.example.scon.global.error.ErrorCode;

public class DtoConvertException extends BusinessException {

    public DtoConvertException(ErrorCode errorCode) {
        super(errorCode, errorCode.getMessage());
    }

    public DtoConvertException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public DtoConvertException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
