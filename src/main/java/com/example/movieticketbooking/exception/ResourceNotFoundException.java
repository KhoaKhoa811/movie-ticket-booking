package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(Code errorCode) {
        super(errorCode);
    }
}
