package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.ErrorCode;

public class DuplicateCreateException extends BaseException {

    public DuplicateCreateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
