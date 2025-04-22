package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.enums.Code;
import com.nimbusds.jose.JOSEException;
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

    @ExceptionHandler(value = JOSEException.class)
    public ResponseEntity<ApiResponse<?>> handlingJOSEException(JOSEException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(Code.TOKEN_INVALID.getCode())
                .message(Code.TOKEN_INVALID.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = InvalidTokenSignatureException.class)
    public ResponseEntity<ApiResponse<?>> handlingJOSEException(InvalidTokenSignatureException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getErrorCode().getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
