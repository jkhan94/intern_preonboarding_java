package com.sparta.intern_preonboarding_java.domain.user.entity;


import com.sparta.intern_preonboarding_java.common.utill.Timestamped;
import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import com.sparta.intern_preonboarding_java.domain.user.enums.UserState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserState state;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType auth;

    @Column()
    private String refreshToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> userRole;

    @Builder
    public User(String username, String password, String nickname, UserState state, RoleType auth) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.state = state;
        this.auth = auth;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateState(UserState state) {
        this.state = state;
    }
}
