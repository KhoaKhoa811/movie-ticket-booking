package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class BadRequestException extends BaseException {
    public BadRequestException(Code code) {
        super(code);
    }
}
