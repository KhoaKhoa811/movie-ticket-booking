package com.example.movieticketbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncagorized error"),
    JSON_FORMAT_INVALID(9998, "JSON format invalid"),
    GENRE_ALREADY_EXIST(102, "genre already exist"),
    GENRE_INVALID(103, "Genre invalid"),
    GENRE_NOT_FOUND(104, "Genre not found"),
    ;

    private final int code;
    private final String message;
}
