package com.example.scon.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYSTEM000", "서비스에 장애가 발생했습니다."),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, "SYSTEM001", "유효하지 않은 요청입니다."),

    EMAIL_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "EMAIL000", "메일 인증코드가 일치하지 않습니다."),

    USER_NICKNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "USER000", "사용중인 닉네임입니다."),
    USER_NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "USER001", "닉네임은 2~8자로만 설정 가능합니다."),

    // 추가_이민정
    USER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "USER002", "비밀번호가 일치하지 않습니다."),
    USER_PASSWORD_FORMAT_INVALID(HttpStatus.BAD_REQUEST, "USER003", "비밀번호는 영문, 숫자, 특수문자 조합으로 이루어진 8~15자의 문자열로만 설정 가능합니다."),
    USER_PROFILE_IMAGE_TOO_LARGE(HttpStatus.BAD_REQUEST, "USER004", "프로필 이미지 파일의 크기가 너무 큽니다."),
    INVALID_REPORT_TARGET(HttpStatus.BAD_REQUEST, "USER005", "유효하지 않은 신고 대상입니다."),
    DUPLICATE_ACTION(HttpStatus.BAD_REQUEST, "USER006", "이미 해당 서비스를 이용한 사용자입니다."),

    EVENT_NOT_ENDED(HttpStatus.BAD_REQUEST, "EVENT002", "예매한 공연이 아직 종료되지 않아 작성하실 수 없습니다."),

    // 추가_박진우
    EVENT_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "EVENT000", "로그인 후 사용 가능합니다."),
    EVENT_ONLY_OVEN(HttpStatus.UNAUTHORIZED, "EVENT001", "오븐 인증 후 사용 가능합니다."),

    FOLLOW_NOT_FOUND(HttpStatus.NOT_FOUND, "FOLLOW000", "그룹 아이디에 해당하는 그룹이 존재하지 않습니다."),
    FOLLOW_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "FOLLOW001", "로그인 후 사용 가능합니다."),
    RESERVE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESERVE000", "공연 아이디에 해당하는 그룹이 존재하지 않습니다"),
    RESERVE_ALREADY_END(HttpStatus.BAD_REQUEST, "RESERVE001", "이미 종료된 행사입니다."),
    RESERVE_ALREADY_PROCESSED(HttpStatus.BAD_REQUEST, "RESERVE002", "이미 접수처리 된 행사입니다."),
    RESERVE_NOT_RESERVED(HttpStatus.FORBIDDEN, "RESERVE003", "예매한 행사가 아닙니다."),
    RESERVE_QR_FAILED(HttpStatus.BAD_REQUEST, "RESERVE004", "QR 읽기에 실패했습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}