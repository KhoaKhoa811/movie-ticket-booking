package com.example.movieticketbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Code {
    UNCATEGORIZED_EXCEPTION(9999, "Uncagorized error"),
    JSON_FORMAT_INVALID(9998, "JSON format invalid"),
    CONTENT_TYPE_INVALID(9997, "Content type invalid"),
    // genre code
    GENRE_CREATED(100, "Genre created"),
    GENRE_GET_ALL(101, "Genre retrieved"),
    GENRE_ALREADY_EXIST(102, "genre already exist"),
    GENRE_INVALID(103, "Genre invalid"),
    GENRE_NOT_FOUND(104, "Genre not found"),
    // file code
    MULTIPART_FILE_NOT_FOUND(9989, "multipart file not found"),
    FAIL_TO_CONVERT_FILE(9988, "failed to convert file"),
    FAIL_TO_DELETE_FILE(9987, "failed to delete file"),
    IO_ERROR(9986, "IO error"),
    FAIL_TO_UPDATE_FILE(9985, "failed to update file"),
    // cinema code
    CINEMA_CREATED(200, "Cinema created"),
    CINEMA_NAME_INVALID(201, "cinema name invalid"),
    CINEMA_ADDRESS_INVALID(202, "cinema address invalid"),
    CINEMA_PHONE_INVALID(203, "cinema phone invalid"),
    CINEMA_ALREADY_EXIST(204, "cinema already exist"),
    ;

    private final int code;
    private final String message;
}
