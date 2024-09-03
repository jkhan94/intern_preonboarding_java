package com.sparta.intern_preonboarding_java.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.intern_preonboarding_java.common.config.JwtConfig;
import com.sparta.intern_preonboarding_java.common.exception.ErrorCode;
import com.sparta.intern_preonboarding_java.common.security.dto.SecurityDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        SecurityDto resDto;
        if (request.getAttribute(JwtConfig.EXPIRED_TOKEN) != null && (boolean) request.getAttribute(JwtConfig.EXPIRED_TOKEN)) {
            response.setStatus(ErrorCode.EXPIRED_TOKEN.getStatus().value());
            resDto = new SecurityDto(ErrorCode.EXPIRED_TOKEN.getStatus().value(), ErrorCode.EXPIRED_TOKEN.getMessage());
        } else {
            response.setStatus(ErrorCode.USER_NOT_AUTHENTICATED.getStatus().value());
            resDto = new SecurityDto(ErrorCode.USER_NOT_AUTHENTICATED.getStatus().value(), ErrorCode.USER_NOT_AUTHENTICATED.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(resDto));
    }
}
