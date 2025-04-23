package com.example.movieticketbooking.utils;

import com.example.movieticketbooking.entity.CinemaHallSeatEntity;
import com.example.movieticketbooking.entity.ShowEntity;
import com.example.movieticketbooking.enums.SeatType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TicketUtils {
    public static Double calculatePrice(CinemaHallSeatEntity seat) {
        return switch (seat.getType()) {
            case NORMAL -> 10.0;
            case VIP -> 15.0;
            case COUPLE -> 18.0;
        };
    }

    public static String generateTicketCode(ShowEntity show, CinemaHallSeatEntity seat) {
        return "TKT-" + show.getId() + "-" + seat.getId() + "-" + UUID.randomUUID().toString().substring(0, 6);
    }
}
