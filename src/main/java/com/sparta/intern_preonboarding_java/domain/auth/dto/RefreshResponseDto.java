package com.sparta.intern_preonboarding_java.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RefreshResponseDto {

    @Schema(description = "refresh token이 유효하면 access token 재발급", name = "accessToken", type = "String", example = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKSU4gSE8iLCJleHAiOjE3MjUzODE3MDUsInN0YXRlIjoiQUNUSVZFIiwiYXV0aCI6IlJPTEVfVVNFUiIsImlhdCI6MTcyNTM3OTkwNX0.djULLWaZLFa3wKAXj13f4A6Z-gbJwys_5tzXD2I2hoM")
    private String accessToken;

    public RefreshResponseDto(String token) {
        this.accessToken = token;
    }
}
