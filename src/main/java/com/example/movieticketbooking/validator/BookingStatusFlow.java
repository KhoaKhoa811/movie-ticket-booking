package com.example.movieticketbooking.validator;

import com.example.movieticketbooking.enums.BookingStatus;

import java.util.Set;

public class BookingStatusFlow {

    public static boolean isValidTransition(BookingStatus from, BookingStatus to) {
        return switch (from) {
            case PENDING -> Set.of(
                    BookingStatus.CONFIRMED,
                    BookingStatus.FAILED,
                    BookingStatus.CANCELED).contains(to);
            case CONFIRMED -> Set.of(
                    BookingStatus.CHECKED_IN,
                    BookingStatus.REFUNDED,
                    BookingStatus.CANCELED).contains(to);
            default -> false; // Không cho đổi trạng thái khác
        };
    }
}
