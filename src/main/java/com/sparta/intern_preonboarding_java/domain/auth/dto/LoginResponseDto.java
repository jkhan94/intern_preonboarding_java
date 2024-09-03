package com.sparta.intern_preonboarding_java.domain.auth.dto;

import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    @Schema(description = "계정 권한", name = "auth", type = "RoleType", example = "ROLE_USER")
    private RoleType auth;

    @Schema(description = "로그인 성공 시 access token 발급", name = "accessToken", type = "String", example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKSU4gSE8iLCJleHAiOjE3MjUzODE0MTEsInN0YXRlIjoiQUNUSVZFIiwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTcyNTM3OTYxMX0.gxCS1ZSlz39njBIY1fF-JrG0l-NJm2xUtu8dMDZWkaQ")
    private String accessToken;

    @Schema(description = "로그인 성공 시 refresh token 발급", name = "refreshToken", type = "String", example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKSU4gSE8iLCJleHAiOjE3MjY1ODkyMTEsInN0YXRlIjoiQUNUSVZFIiwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTcyNTM3OTYxMX0.TYXp_OOyQz5ChZU4mdyL9m65Y7kkhaIwxknr_KtWDOw")
    private String refreshToken;

    @Builder
    public LoginResponseDto(RoleType auth, String accessToken, String refreshToken) {
        this.auth = auth;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
