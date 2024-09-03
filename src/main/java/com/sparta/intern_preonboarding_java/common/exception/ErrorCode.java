package com.sparta.intern_preonboarding_java.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    EXIST_USER(HttpStatus.CONFLICT, "이미 존재하는 유저입니다."),
    USER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),

    // 토큰
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "이미 만료된 토큰입니다");

    private final HttpStatus status;
    private final String message;
}