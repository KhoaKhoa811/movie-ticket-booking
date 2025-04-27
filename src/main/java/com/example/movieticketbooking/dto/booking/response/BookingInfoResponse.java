package com.example.movieticketbooking.dto.booking.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingInfoResponse {
    private Integer bookingId;
    private String accountEmail;
    private String movieName;
    private String cinemaName;
    private String hallName;
    private List<String> seatName;
    private String showTime;
    private List<Double> seatPrice;
    private Double totalPrice;
}
