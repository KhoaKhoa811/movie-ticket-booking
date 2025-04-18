package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class ConflictException extends BaseException {
    public ConflictException(Code code) {
        super(code);
    }
}
