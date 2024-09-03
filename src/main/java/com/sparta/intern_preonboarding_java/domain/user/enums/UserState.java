package com.sparta.intern_preonboarding_java.domain.user.enums;

public enum UserState {
    ACTIVE(State.ACTIVE_STATE),
    WITHDRAW(State.WITHDRAW);

    private final String state;

    UserState(String state) {
        this.state = state;
    }

    public String getStateString() {
        return state;
    }

    private static class State {
        public static final String ACTIVE_STATE = "정상";
        public static final String WITHDRAW = "회원탈퇴";
    }
}