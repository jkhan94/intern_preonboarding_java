package com.sparta.intern_preonboarding_java.domain.user.entity;

import com.sparta.intern_preonboarding_java.domain.user.enums.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private RoleType roleType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Role(RoleType roleType, User user) {
        this.roleType = roleType;
        this.user = user;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Role userRole = (Role) object;
        return Objects.equals(user.getUsername(), userRole.getUser().getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}