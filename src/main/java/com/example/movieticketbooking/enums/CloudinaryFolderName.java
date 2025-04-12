package com.example.movieticketbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CloudinaryFolderName {
    CINEMA("cinema"),
    MOVIE("movie"),
    ;

    private final String cinemaFolder;
}
