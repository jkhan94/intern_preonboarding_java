package com.sparta.intern_preonboarding_java.domain.auth.service;

import com.sparta.intern_preonboarding_java.common.config.JwtConfig;
import com.sparta.intern_preonboarding_java.common.exception.BusinessException;
import com.sparta.intern_preonboarding_java.common.exception.ErrorCode;
import com.sparta.intern_preonboarding_java.common.jwt.JwtProvider;
import com.sparta.intern_preonboarding_java.common.security.UserDetailsImpl;
import com.sparta.intern_preonboarding_java.domain.auth.dto.LoginRequestDto;
import com.sparta.intern_preonboarding_java.domain.auth.dto.SignupRequestDto;
import com.sparta.intern_preonboarding_java.domain.auth.dto.SignupResponseDto;
import com.sparta.intern_preonboarding_java.domain.user.entity.User;
import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import com.sparta.intern_preonboarding_java.domain.user.enums.UserState;
import com.sparta.intern_preonboarding_java.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    @Value("${ADMIN_TOKEN}")
    String adminToken;

    @Transactional
    public SignupResponseDto signUp(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        RoleType userAuth = RoleType.USER;

        if (userRepository.findByUsername(username).isPresent()) {
            throw new BusinessException(ErrorCode.EXIST_USER);
        }

        if (signupRequestDto.getAdminToken().equals(adminToken)) {
            userAuth = RoleType.ADMIN;
        }

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .nickname(signupRequestDto.getNickname())
                .state(UserState.ACTIVE)
                .auth(userAuth)
                .build();

        userRepository.save(user);

        return SignupResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .authorities(user.getAuth())
                .build();
    }

    @Transactional
    public List<String> login(LoginRequestDto loginRequestDto) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword(),
                        null
                )
        );

        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();
        String accessToken = jwtProvider.createToken(user, JwtConfig.accessTokenTime);
        String refreshToken = jwtProvider.createToken(user, JwtConfig.refreshTokenTime);

        User curUser = findByUserName(user.getUsername());
        curUser.updateRefreshToken(refreshToken);

        List<String> tokenList = new ArrayList<>();
        tokenList.add(accessToken);
        tokenList.add(refreshToken);

        return tokenList;
    }

    @Transactional
    public void signOut(User user) {

        User curUser = findByUserName(user.getUsername());
        curUser.updateState(UserState.WITHDRAW);
        curUser.updateRefreshToken("");
    }

    @Transactional
    public void logout(User user) {
        User curUser = findByUserName(user.getUsername());
        curUser.updateRefreshToken("");
    }

    public String refreshToken(User user, String refreshToken) {
        User curUser = findByUserName(user.getUsername());

        if (!curUser.getRefreshToken().equals(refreshToken)) {
            throw new BusinessException(ErrorCode.USER_NOT_AUTHENTICATED);
        }

        String newAccessToken = jwtProvider.createToken(curUser, JwtConfig.accessTokenTime);

        return newAccessToken;
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
    }

    public User findById(long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public RoleType getUserRole(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new BusinessException(ErrorCode.USER_NOT_FOUND)
        );

        return user.getAuth();
    }
}
