package com.sparta.intern_preonboarding_java.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank(message = "id는 필수입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "이름은 필수로 입력해야 합니다.")
    private String nickname;

    @NotBlank(message = "관리자 계정으로 가입하려면 비밀키를 입력하세요.")
    private String adminToken = "";
}