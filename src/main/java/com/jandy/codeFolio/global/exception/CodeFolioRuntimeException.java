package com.jandy.codeFolio.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
public class CodeFolioRuntimeException extends RuntimeException {

    private final HttpStatus status;
    private final String message;
    private final String code;

    public CodeFolioRuntimeException(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.code = errorCode.name();

        log.error("CodeFolioRuntimeException 발생: status={}, message={}", status, message, this);
    }
}