package com.sparta.intern_preonboarding_java.common.config;

import com.sparta.intern_preonboarding_java.common.jwt.JwtProvider;
import com.sparta.intern_preonboarding_java.common.security.AuthenticationEntryPointImpl;
import com.sparta.intern_preonboarding_java.common.security.JwtAuthenticationFilter;
import com.sparta.intern_preonboarding_java.common.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.sessionManagement(
                (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(
                (authorizationHttpRequests) -> authorizationHttpRequests
                        .requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/sign").permitAll()

                        // Swagger 관련 경로에 대해 permitAll() 추가
                        .requestMatchers(
                                "/v3/api-docs/**",       // OpenAPI 문서 경로
                                "/swagger-ui/**",        // Swagger UI 리소스 경로
                                "/swagger-ui.html",      // Swagger UI HTML 경로
                                "/swagger-resources/**", // Swagger 리소스 경로 (필요한 경우)
                                "/webjars/**"            // 웹자르 리소스 경로 (필요한 경우)
                        ).permitAll()

                        .anyRequest().authenticated()
        );

        http.exceptionHandling((e) ->
                e.authenticationEntryPoint(authenticationEntryPoint));
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}