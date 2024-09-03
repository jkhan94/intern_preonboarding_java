package com.sparta.intern_preonboarding_java.domain.auth.dto;

import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String username;
    private String nickname;
    private RoleType authorities;

    @Builder
    public SignupResponseDto(String username, String nickname, RoleType authorities) {
        this.username = username;
        this.nickname = nickname;
        this.authorities = authorities;
    }
}
