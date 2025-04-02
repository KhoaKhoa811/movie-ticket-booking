package com.example.movieticketbooking.exception;

import com.example.movieticketbooking.enums.Code;

public class FileProcessingException extends BaseException {
    public FileProcessingException(Code code) {
        super(code);
    }
}
