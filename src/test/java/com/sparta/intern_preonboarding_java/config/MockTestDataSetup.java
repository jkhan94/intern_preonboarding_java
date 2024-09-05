package com.sparta.intern_preonboarding_java.config;

import com.sparta.intern_preonboarding_java.common.config.JwtConfig;
import com.sparta.intern_preonboarding_java.domain.user.entity.User;
import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import com.sparta.intern_preonboarding_java.domain.user.enums.UserState;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class MockTestDataSetup {
    public static User mockTestUserSetup() {
        String TEST_USER_NAME = "username";
        String TEST_USER_PASSWORD = "password";
        String TEST_USER_NICKNAME = "nickname";
        return User.builder()
                .username(TEST_USER_NAME)
                .password(TEST_USER_PASSWORD)
                .nickname(TEST_USER_NICKNAME)
                .auth(RoleType.USER)
                .state(UserState.ACTIVE)
                .build();
    }

    public static String mockTestTokenSetup(User user, Long TEST_TOKEN_TIME) {
        Date TEST_DATE = new Date();

        return JwtConfig.BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(TEST_DATE.getTime() + TEST_TOKEN_TIME))
                        .claim(JwtConfig.USER_STATE_KEY, user.getState())
                        .claim(JwtConfig.USER_AUTH_KEY, user.getAuth())
                        .setIssuedAt(TEST_DATE)
                        .signWith(JwtConfig.key, JwtConfig.signatureAlgorithm)
                        .compact();
    }

}
