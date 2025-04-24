package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class IllegalStateException extends BaseException {
    public IllegalStateException(Code errorCode) {
        super(errorCode);
    }
}
