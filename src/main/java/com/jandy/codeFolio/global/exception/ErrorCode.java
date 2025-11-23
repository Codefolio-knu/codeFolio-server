package com.jandy.codeFolio.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 유저 관련 에러
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_MAIL_INVALID(HttpStatus.BAD_REQUEST, "유효하지 메일 형식입니다. knu.ac.kr 메일을 입력해주세요,"),
    USER_NOT_ACCESS_FORBIDDEN(HttpStatus.BAD_REQUEST, "접근 권한이 없는 사용자입니다"),
    USER_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "사용자 저장에 실패했습니다."),
    USER_MAIL_NOTFOUND(HttpStatus.INTERNAL_SERVER_ERROR, "먼저 메일 인증을 진행해주세요."),
    USER_CODE_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "유효하지 않는 인증번호입니다."),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다."),

    // 기술스택 관련 에러
    SKILL_NOT_FOUND(HttpStatus.NOT_FOUND, "기술 스택을 찾을 수 없습니다."),

    // 성과 관련 에러
    ACHIEVEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "성과를 찾을 수 없습니다."),

    // 게시물 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."),

    // 프로젝트 관련 에러
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "프로젝트를 찾을 수 없습니다."),

    // 지원 관련 에러
    APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "지원서를 찾을 수 없습니다."),
    USER_ALREADY_APPLIED(HttpStatus.CONFLICT, "이미 지원한 게시물입니다."),
    CANNOT_APPLY_TO_OWN_POST(HttpStatus.BAD_REQUEST, "자신의 게시물에는 지원할 수 없습니다."),
    CAPACITY_FULL(HttpStatus.BAD_REQUEST, "모집 인원이 마감되었습니다."),

    // 권한 관련 에러
    NO_AUTHORITY(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
