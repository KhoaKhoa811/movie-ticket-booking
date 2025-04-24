package com.example.movieticketbooking.dto.booking.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private List<Integer> ticketIds;
    private Integer accountId;
}
