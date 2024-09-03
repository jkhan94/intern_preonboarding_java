package com.sparta.intern_preonboarding_java.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleType {
    USER(UserType.USER),
    ADMIN(UserType.ADMIN);

    private final String userAuth;

    RoleType(String userAuth) {
        this.userAuth = userAuth;
    }

    @JsonValue
    public String getUserAuth() {
        return userAuth;
    }

    @JsonCreator
    public static RoleType toJson(String auth) {
        for (RoleType type : RoleType.values()) {
            if (type.getUserAuth().equals(auth)) {
                return type;
            }
        }
        return null;
    }

    private static class UserType {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}