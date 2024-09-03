package com.sparta.intern_preonboarding_java.domain.auth.dto;

import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    RoleType auth;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginResponseDto(RoleType auth, String accessToken, String refreshToken) {
        this.auth = auth;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
