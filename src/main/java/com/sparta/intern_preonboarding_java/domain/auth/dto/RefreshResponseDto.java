package com.sparta.intern_preonboarding_java.domain.auth.dto;

import lombok.Getter;

@Getter
public class RefreshResponseDto {
    private String accessToken;

    public RefreshResponseDto(String token) {
        this.accessToken = token;
    }
}
