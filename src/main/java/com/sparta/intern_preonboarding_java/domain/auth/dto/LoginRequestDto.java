package com.sparta.intern_preonboarding_java.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @Schema(description = "로그인 아이디", name = "username", type = "String", example = "JIN HO")
    @NotBlank
    private String username;

    @Schema(description = "로그인 비밀번호", name = "password", type = "String", example = "12341234")
    @NotBlank
    private String password;
}
