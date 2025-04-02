package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class ResourceAlreadyExistsException extends BaseException {

    public ResourceAlreadyExistsException(Code errorCode) {
        super(errorCode);
    }
}
