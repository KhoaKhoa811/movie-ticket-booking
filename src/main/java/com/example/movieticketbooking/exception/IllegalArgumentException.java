package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class IllegalArgumentException extends BaseException {
    public IllegalArgumentException(Code code) {
        super(code);
    }
}
