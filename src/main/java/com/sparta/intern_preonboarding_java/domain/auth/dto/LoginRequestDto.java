package com.sparta.intern_preonboarding_java.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
