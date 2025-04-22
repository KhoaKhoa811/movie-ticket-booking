package com.example.movieticketbooking.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.tools.Diagnostic;

@AllArgsConstructor
@Getter
public enum Code {
    UNCATEGORIZED_EXCEPTION(9999, "Uncagorized error"),
    JSON_FORMAT_INVALID(9998, "JSON format invalid"),
    CONTENT_TYPE_INVALID(9997, "Content type invalid"),
    BAD_CREDENTIALS(9996, "Bad credentials"),
    USERNAME_NOT_FOUND(9995, "Username not exists"),
    TOKEN_INVALID(9994, "Invalid or malformed token"),
    EMAIL_SENT_SUCCESS(9993, "Email sent successfully"),
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
    CINEMA_NOT_FOUND(205, "cinema not found"),
    CINEMA_GET_ALL(206, "Cinema retrieved"),
    CINEMA_DELETED(207, "Cinema deleted successfully"),
    CINEMA_UPDATED(208, "Cinema updated successfully"),
    // city code
    CITY_CREATED(300, "City created"),
    CITY_GET_ALL(301, "City retrieved"),
    CITY_INVALID(302, "City invalid"),
    CITY_NOT_FOUND(303, "City not found"),
    CITY_ALREADY_EXIST(304, "City already exist"),
    CITY_DELETED(305, "City deleted successfully"),
    CITY_UPDATED(306, "City updated successfully"),
    // cinema image code
    CINEMA_IMAGES_CREATED(400, "Cinema images created"),
    CINEMA_IMAGES_DELETED(401, "Cinema image deleted"),
    CINEMA_IMAGES_NOT_FOUND(402, "Cinema image not found"),
    CINEMA_IMAGES_GET(403, "Cinema images retrieved"),
    // cinema hall code
    CINEMA_HALL_CREATED(500, "Cinema hall created"),
    CINEMA_HALL_DELETED(501, "Cinema hall deleted"),
    CINEMA_HALL_NOT_FOUND(502, "Cinema hall not found"),
    CINEMA_HALL_GET_ALL(503, "Cinema hall retrieved"),
    CINEMA_HALL_NAME_INVALID(504, "Cinema hall name invalid"),
    CINEMA_HALL_ROW_COUNT_INVALID(505, "Cinema hall row count invalid"),
    CINEMA_HALL_COLUMN_COUNT_INVALID(506, "Cinema hall column count invalid"),
    CINEMA_HALL_ACTIVE_INVALID(507, "Cinema hall active invalid"),
    CINEMA_HALL_ROW_INVALID(508, "Cinema hall row invalid"),
    CINEMA_HALL_COLUMN_INVALID(509, "Cinema hall column invalid"),
    CINEMA_HALL_UPDATED(510, "Cinema hall updated successfully" ),
    // seat template
    SEAT_TEMPLATE_INVALID(511, "seat template invalid"),
    SEAT_TEMPLATE_GET_ALL(512, "seat template retrieved"),
    SEAT_TEMPLATE_NOT_FOUND(513, "seat template not found"),
    // cinema hall seat
    SEAT_GET_ALL(600, "seat retrieved"),
    SEAT_UPDATED(601, "seat updated successfully"),
    SEAT_NOT_FOUND(602, "seat does not exist"),
    // movie
    MOVIE_CREATED(700, "Movie created"),
    MOVIE_GET_ALL(701, "Movie retrieved"),
    MOVIE_NOT_FOUND(702, "Movie not found"),
    MOVIE_DELETED(703, "Movie deleted successfully"),
    MOVIE_UPDATED(704, "Movie updated successfully"),
    MOVIE_TITLE_INVALID(705, "Movie title invalid"),
    MOVIE_DESCRIPTION_INVALID(706, "Movie description invalid"),
    MOVIE_DURATION_INVALID(707, "Movie duration invalid"),
    MOVIE_RELEASE_DATE_INVALID(708, "Movie release date invalid"),
    MOVIE_TYPE_INVALID(709, "Movie type invalid"),
    MOVIE_GENRE_INVALID(710, "Movie genre invalid"),
    MOVIE_ALREADY_EXIST(711, "Movie already exist"),
    MOVIE_IMAGE_UPDATED(712, "Movie image updated successfully"),
    // shows
    SHOWS_CREATED(800, "Shows created" ),
    SHOWS_DELETED(801, "Shows deleted" ),
    SHOWS_NOT_FOUND(802, "Shows not found" ),
    SHOWS_GET(803, "Shows retrieved" ),
    SHOWS_GET_ALL(804, "Shows retrieved" ),
    SHOWS_UPDATED(805, "Shows updated successfully"),
    SHOWS_ALREADY_EXIST(806, "Shows already exist" ),
    SHOWS_CONFLICT_WITH_EXISTING_SHOWS(807, "Shows conflict with existing shows"),
    SHOWS_CONFLICT_WITHIN_REQUEST(808, "Shows conflict within request"),
    // account
    ACCOUNT_EMAIL_NOT_FOUND(1200, "Account email not found" ),
    ACCOUNT_EMAIL_ALREADY_EXIST(1201, "Account email already exist"),
    ACCOUNT_NOT_FOUND(1202, "Account not found"),
    ACCOUNT_DELETED(1203, "Account deleted successfully"),
    ACCOUNT_GET_ALL(1204, "Account retrieved"),
    ACCOUNT_UPDATED(1205, "Account updated successfully"),
    ACCOUNT_GET(1206, "Account retrieved"),
    // jwt
    JWT_CREATED(1300, "JWT Created"),
    JWT_INVALID(1301, "JWT Invalid"),
    // permission
    PERMISSION_CREATED(1400, "Permission created"),
    PERMISSION_GET_ALL(1401, "Permission retrieved"),
    PERMISSION_NOT_FOUND(1402, "Permission not found"),
    PERMISSION_DELETED(1403, "Permission deleted successfully"),
    PERMISSION_UPDATED(1404, "Permission updated successfully"),
    PERMISSION_ALREADY_EXIST(1405, "Permission already exist" ),
    // role
    ROLE_CREATED(1500, "Role created"),
    ROLE_GET_ALL(1501, "Role retrieved"),
    ROLE_NOT_FOUND(1502, "Role not found"),
    ROLE_DELETED(1503, "Role deleted successfully"),
    ROLE_UPDATED(1504, "Role updated successfully"),
    ROLE_ALREADY_EXIST(1505, "Role already exist" ),
    // login
    LOGIN_SUCCESS(1600, "Login success"),
    REGISTER_SUCCESS(1601, "Register success"),
    REGISTER_PROCESS(1602, "Register process"),
    VERIFY_TOKEN_INVALID(1603, "Verify token invalid"),
    ACCOUNT_ALREADY_VERIFY(1604, "Account already verify"),
    ;

    private final int code;
    private final String message;
}
