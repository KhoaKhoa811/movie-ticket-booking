package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.enums.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<?>> handlingException(Exception exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(Code.UNCATEGORIZED_EXCEPTION.getCode())
                .message(Code.UNCATEGORIZED_EXCEPTION.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<?>> handlingDuplicateCreateException(ResourceAlreadyExistsException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getErrorCode().getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        Code errorCode = Code.valueOf(enumKey);
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handlingHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(Code.JSON_FORMAT_INVALID.getCode())
                .message(Code.JSON_FORMAT_INVALID.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handlingResourceNotFoundException(ResourceNotFoundException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getErrorCode().getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = FileProcessingException.class)
    public ResponseEntity<ApiResponse<?>> handlingFileProcessingException(FileProcessingException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(exception.getErrorCode().getCode())
                .message(exception.getErrorCode().getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<ApiResponse<?>> handlingIOException(IOException exception) {
        ApiResponse<?> exceptionResponse = ApiResponse.builder()
                .code(Code.IO_ERROR.getCode())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
