package com.example.movieticketbooking.utils;

import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.IllegalArgumentException;

public class CinemaHallUtils {
    public static int calculateTotalSeats(Integer rowCount, Integer columnCount) {
        if (rowCount == null || columnCount == null || rowCount < 1 || columnCount < 1) {
            throw new IllegalArgumentException(Code.ROW_COLUMN_ILLEGAL);
        }
        return rowCount * columnCount;
    }
}
