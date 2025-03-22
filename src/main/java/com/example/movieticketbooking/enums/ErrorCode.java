package com.example.movieticketbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncagorized error"),
    DUPLICATED_GENRE(102, "Duplicated genre")
    ;

    private final int code;
    private final String message;
}
