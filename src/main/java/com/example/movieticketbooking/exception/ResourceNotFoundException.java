package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.ErrorCode;

public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
