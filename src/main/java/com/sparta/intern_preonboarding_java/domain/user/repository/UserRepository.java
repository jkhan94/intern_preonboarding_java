package com.sparta.intern_preonboarding_java.domain.user.repository;

import com.sparta.intern_preonboarding_java.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
