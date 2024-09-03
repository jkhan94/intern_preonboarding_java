package com.sparta.intern_preonboarding_java.domain.auth.controller;

import com.sparta.intern_preonboarding_java.common.config.JwtConfig;
import com.sparta.intern_preonboarding_java.common.security.UserDetailsImpl;
import com.sparta.intern_preonboarding_java.domain.auth.dto.*;
import com.sparta.intern_preonboarding_java.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 인증 관련 요청을 처리하는 컨트롤러입니다.
 * <p>
 * 이 컨트롤러는 회원가입, 로그인, 로그아웃, 토큰 갱신 등의 인증 작업을 담당합니다.
 * </p>
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUp(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto responseDto = authService.signUp(signupRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    @PostMapping("/sign")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        List<String> tokens = authService.login(loginRequestDto);
        String accessToken = tokens.get(0);
        String refreshToken = tokens.get(1);

        response.addHeader(JwtConfig.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtConfig.REFRESH_TOKEN_HEADER, refreshToken);

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .auth(authService.getUserRole(loginRequestDto.getUsername()))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok().body(loginResponseDto);
    }


    /**
     * 리프레시 토큰을 이용해 새로운 액세스 토큰을 발급합니다.
     *
     * @param userDetails
     *         인증된 사용자 정보를 포함한 객체
     * @param response
     *         HTTP 응답 객체로, 여기서 새로운 액세스 토큰을 헤더에 추가할 수 있습니다.
     * @param request
     *         HTTP 요청 객체로, 여기서 기존 리프레시 토큰을 헤더에서 가져옵니다.
     * @return 새로운 액세스 토큰을 포함한 응답 DTO를 반환합니다.
     */
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refreshToken(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        String accessToken = authService.refreshToken(userDetails.getUser(), request.getHeader(JwtConfig.AUTHORIZATION_HEADER));

        response.addHeader(JwtConfig.ACCESS_TOKEN_HEADER, accessToken);

        RefreshResponseDto resDto = new RefreshResponseDto(accessToken);

        return ResponseEntity.ok().body(resDto);
    }

}
