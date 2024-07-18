package com.example.scon.global.error;

import com.example.scon.global.error.response.ErrorResponse;
import com.example.scon.global.error.response.ExceptionResponse;
import com.example.scon.global.error.type.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(basePackages = {
        "com.example.scon.domain.auth.controller",
        "com.example.scon.domain.email.controller",
        "com.example.scon.domain.member.controller",
})
@Slf4j
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorResponse> createErrorResponse(ErrorCode errorCode) {
        return new ResponseEntity<>(ErrorResponse.of(errorCode.getCode(), errorCode.getMessage()),
                errorCode.getHttpStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
        log.error("Error: ", e);
        return new ResponseEntity<>(ExceptionResponse.of(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e) {
        log.error("ForbiddenException: ", e);
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDataNotFoundException(DataNotFoundException e) {
        log.error("DataNotFoundException: ", e);
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        log.error("Error: ", e);
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("Error: ", e);
        return createErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Error: ", e);
        return createErrorResponse(ErrorCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Error: ", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        // 유효성 검사 에러 메시지 가져오기
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();

        // ErrorResponse 객체를 생성하여 ResponseEntity로 반환
        ErrorResponse errorResponse = new ErrorResponse("VALIDATION_ERROR", errorMessage);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}
