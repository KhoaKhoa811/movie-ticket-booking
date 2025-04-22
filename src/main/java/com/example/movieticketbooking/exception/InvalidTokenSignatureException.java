package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class InvalidTokenSignatureException extends BaseException {
    public InvalidTokenSignatureException(Code errorCode) {
        super(errorCode);
    }
}
