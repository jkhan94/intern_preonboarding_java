package com.sparta.intern_preonboarding_java.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        return ResponseEntity
                .status(ex.getStatus() == HttpStatus.NO_CONTENT ? HttpStatus.OK : ex.getStatus())
                .body(ex.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBeanValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    return String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage());
                }).collect(Collectors.joining("\n"));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleUnhandledException(Exception e, HttpServletRequest request) {
        log.info("", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException ex) {
        // Custom handling logic
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ex.getMessage());
    }
}






