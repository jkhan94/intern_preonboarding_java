package com.sparta.intern_preonboarding_java.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Schema(description = "로그인 아이디", name = "username", type = "String", example = "JIN HO")
    @NotBlank(message = "id는 필수입니다.")
    private String username;

    @Schema(description = "로그인 비밀번호", name = "password", type = "String", example = "12341234")
    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Schema(description = "별명", name = "nickname", type = "String", example = "Mentos")
    @NotBlank(message = "별명은 필수로 입력해야 합니다.")
    private String nickname;

    @Schema(description = "관리자 계정으로 가입 시 비밀키 입력 필드", name = "adminToken", type = "String", example = "관리자 계정으로 가입 시 필요한 비밀키")
    private String adminToken = "";
}