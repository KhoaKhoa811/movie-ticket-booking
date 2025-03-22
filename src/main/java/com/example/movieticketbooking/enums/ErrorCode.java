package com.example.movieticketbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncagorized error"),
    JSON_FORMAT_INVALID(9998, "JSON format invalid"),
    DUPLICATED_GENRE(102, "Duplicated genre"),
    GENRE_INVALID(103, "Genre invalid"),
    ;

    private final int code;
    private final String message;
}
