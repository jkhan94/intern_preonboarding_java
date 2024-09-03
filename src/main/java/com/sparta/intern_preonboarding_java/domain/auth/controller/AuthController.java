package com.sparta.intern_preonboarding_java.domain.auth.controller;

import com.sparta.intern_preonboarding_java.common.config.JwtConfig;
import com.sparta.intern_preonboarding_java.common.exception.ErrorResponse;
import com.sparta.intern_preonboarding_java.common.security.UserDetailsImpl;
import com.sparta.intern_preonboarding_java.domain.auth.dto.*;
import com.sparta.intern_preonboarding_java.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Auth", description = "회원가입, 로그인 컨트롤러")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "username, 비밀번호, 닉네임을 전달받아 회원가입")
    @Parameters({
            @Parameter(name = "signupRequestDto", description = "회원가입에 필요한 정보: username, 비밀번호, 닉네임")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공 시 username, 닉네임, 유저 권한을 반환."),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 유저입니다.", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUp(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto responseDto = authService.signUp(signupRequestDto);
        return ResponseEntity.ok().body(responseDto);
    }


    @Operation(summary = "로그인", description = "username, 비밀번호를 전달받아 로그인")
    @Parameters({
            @Parameter(name = "loginRequestDto", description = "로그인에 필요한 정보: username, 비밀번호"),
            @Parameter(name = "response", description = "생성된 토큰을 헤더에 담아 클라이언트에 반환")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공 시 계정 권한, access token, refresh token 반환"),
            @ApiResponse(responseCode = "404", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
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


    @Operation(summary = "access token 재발급", description = "refresh token이 유효하면 access token 재발급")
    @Parameters({
            @Parameter(name = "userDetails", description = "인증된 사용자 정보를 포함한 객체"),
            @Parameter(name = "request", description = "HTTP 응답 객체. access token 헤더에 추가."),
            @Parameter(name = "response", description = "HTTP 요청 객체. 헤더에서 refresh token 가져옴")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "refresh token이 유효하면 access token을 재발급하여 반환"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDto> refreshToken(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        String accessToken = authService.refreshToken(userDetails.getUser(), request.getHeader(JwtConfig.REFRESH_TOKEN_HEADER));

        response.addHeader(JwtConfig.ACCESS_TOKEN_HEADER, accessToken);

        RefreshResponseDto resDto = new RefreshResponseDto(accessToken);

        return ResponseEntity.ok().body(resDto);
    }

}
