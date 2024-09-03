package com.sparta.intern_preonboarding_java.domain.auth.dto;

import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponseDto {

    @Schema(description = "로그인 아이디", name = "username", type = "String", example = "JIN HO")
    private String username;

    @Schema(description = "별명", name = "nickname", type = "String", example = "Mentos")
    private String nickname;

    @Schema(description = "계정 권한", name = "auth", type = "RoleType", example = "ROLE_USER")
    private RoleType authorities;

    @Builder
    public SignupResponseDto(String username, String nickname, RoleType authorities) {
        this.username = username;
        this.nickname = nickname;
        this.authorities = authorities;
    }
}
