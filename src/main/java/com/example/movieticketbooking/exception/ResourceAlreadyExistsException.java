package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.ErrorCode;

public class ResourceAlreadyExistsException extends BaseException {

    public ResourceAlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
