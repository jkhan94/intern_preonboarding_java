package com.sparta.intern_preonboarding_java.common.jwt;

import com.sparta.intern_preonboarding_java.common.config.JwtConfig;
import com.sparta.intern_preonboarding_java.config.MockTestDataSetup;
import com.sparta.intern_preonboarding_java.domain.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    public static final int TOKEN_PREFIX_LENGTH = 7;

    @Autowired
    private JwtProvider jwtProvider;

    private User testUser;

    @BeforeEach
    void setUp() {
//        JwtConfig.init();
        testUser = MockTestDataSetup.mockTestUserSetup();
    }

    @Test
    @DisplayName("Access Token 발행 테스트")
    void createAccessToken() {

        // given
        Long tokenTime = JwtConfig.accessTokenTime;

        // when
        String accessToken = jwtProvider.createToken(testUser, tokenTime);

        // then
        assertNotNull(accessToken);
        assertEquals(accessToken.substring(0, 7), JwtConfig.BEARER_PREFIX);
    }

    @Test
    @DisplayName("Access Token 검증 테스트")
    void isAccessTokenValidate() {

        // given
        Long tokenTime = JwtConfig.accessTokenTime;
        String accessToken = MockTestDataSetup.mockTestTokenSetup(testUser, tokenTime).substring(TOKEN_PREFIX_LENGTH);
        HttpServletRequest request = new MockHttpServletRequest();

        // when
        boolean result = jwtProvider.isTokenValidate(accessToken, request);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("Access Token 시간 만료 테스트")
    void isAccessTokenExpired() throws InterruptedException {

        // given
        Long shortTokenTime = 1000L; // 토큰 유효 시간 1초
        String accessToken = MockTestDataSetup.mockTestTokenSetup(testUser, shortTokenTime).substring(7);
        HttpServletRequest request = new MockHttpServletRequest();

        Thread.sleep(1500); // 토큰 만료되도록 1.5초 대기

        // when
        boolean isTokenValid = jwtProvider.isTokenValidate(accessToken, request);
        boolean isTokenExpired = request.getAttribute(com.sparta.intern_preonboarding_java.common.config.JwtConfig.EXPIRED_TOKEN) != null;

        // then
        assertFalse(isTokenValid);
        assertTrue(isTokenExpired);
    }

    @Test
    @DisplayName("Refresh Token 발행 테스트")
    void createRefreshToken() {

        // given
        Long tokenTime = JwtConfig.refreshTokenTime;

        // when
        String refreshToken = jwtProvider.createToken(testUser, tokenTime);

        // then
        assertNotNull(refreshToken);
        assertEquals(refreshToken.substring(0, 7), JwtConfig.BEARER_PREFIX);
    }

    @Test
    @DisplayName("Refresh Token 검증 테스트")
    void isRefreshTokenValidate() {

        // given
        Long tokenTime = JwtConfig.refreshTokenTime;
        String refreshToken = MockTestDataSetup.mockTestTokenSetup(testUser, tokenTime).substring(7);
        HttpServletRequest request = new MockHttpServletRequest();

        // when
        boolean result = jwtProvider.isTokenValidate(refreshToken, request);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("Refresh Token 시간 만료 테스트")
    void isRefreshTokenExpired() throws InterruptedException {

        // given
        Long shortTokenTime = 1000L;
        String refreshToken = MockTestDataSetup.mockTestTokenSetup(testUser, shortTokenTime).substring(7);
        HttpServletRequest request = new MockHttpServletRequest();

        Thread.sleep(1500);

        // when
        boolean isTokenValid = jwtProvider.isTokenValidate(refreshToken, request);
        boolean isTokenExpired = request.getAttribute(com.sparta.intern_preonboarding_java.common.config.JwtConfig.EXPIRED_TOKEN) != null;

        // then
        assertFalse(isTokenValid);
        assertTrue(isTokenExpired);
    }
}