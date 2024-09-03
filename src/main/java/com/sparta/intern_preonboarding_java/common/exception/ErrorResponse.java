package com.sparta.intern_preonboarding_java.common.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    @Schema(description = "예외 발생 시 반환되는 상태코드", name = "code", type = "HttpStatus", example = "Http 상태코드")
    private HttpStatus code;

    @Schema(description = "예외 발생 시 반환되는 메시지", name = "message", type = "String", example = "예외에 맞는 메시지 출력")
    private String message;

    public ErrorResponse(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

}
