package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.enums.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<?>> handlingBadCredentialsException(BadCredentialsException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(Code.BAD_CREDENTIALS.getCode())
                .message(Code.BAD_CREDENTIALS.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlingUsernameNotFoundException(UsernameNotFoundException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(Code.USERNAME_NOT_FOUND.getCode())
                .message(Code.USERNAME_NOT_FOUND.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
