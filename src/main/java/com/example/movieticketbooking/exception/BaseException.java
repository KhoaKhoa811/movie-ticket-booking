package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private final Code errorCode;

    public BaseException(Code errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
