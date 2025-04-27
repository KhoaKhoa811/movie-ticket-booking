package com.example.movieticketbooking.service.booking.impl;

import com.example.movieticketbooking.dto.booking.request.BookingInfoRequest;
import com.example.movieticketbooking.dto.booking.response.BookingInfoResponse;
import com.example.movieticketbooking.entity.*;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.exception.ResourceNotFoundException;
import com.example.movieticketbooking.repository.BookingRepository;
import com.example.movieticketbooking.service.booking.BookingInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingInfoServiceImpl implements BookingInfoService {

    @Override
    public BookingInfoRequest getBookingInfo(BookingEntity bookingEntity) {
        // get tickets from booking
        List<TicketEntity> ticketEntities = bookingEntity.getTickets();
        if (ticketEntities.isEmpty()) {
            throw new ResourceNotFoundException(Code.BOOKING_NOT_FOUND); // Ensure tickets exist
        }

        AccountEntity accountEntity = bookingEntity.getAccount();

        ShowEntity showEntity = ticketEntities.get(0).getShow();
        MovieEntity movieEntity = showEntity.getMovie();

        // Create seat names and prices using a simple for-loop
        List<String> seatNames = new ArrayList<>();
        List<Double> seatPrices = new ArrayList<>();
        for (TicketEntity ticket : ticketEntities) {
            seatNames.add(ticket.getSeat().getSeatRow().toString() + ticket.getSeat().getSeatCol());
            seatPrices.add(ticket.getPrice());
        }

        CinemaHallEntity cinemaHallEntity = ticketEntities.get(0).getSeat().getCinemaHall();
        CinemaEntity cinemaEntity = cinemaHallEntity.getCinema();

        // Use DateTimeFormatter to format the show time
        String showTime = showEntity.getShowDate().toString() + " " + showEntity.getStartTime().toString();

        return BookingInfoRequest.builder()
                .bookingId(bookingEntity.getId())
                .accountEmail(accountEntity.getEmail())
                .cinemaName(cinemaEntity.getName())
                .hallName(cinemaHallEntity.getName())
                .movieName(movieEntity.getTitle())
                .seatName(seatNames)
                .showTime(showTime)
                .seatPrice(seatPrices)
                .totalPrice(bookingEntity.getTotalPrice())
                .build();
    }

    @Override
    public BookingInfoResponse getBookingInfoResponse(BookingEntity bookingEntity) {
        BookingInfoRequest bookingInfoRequest = getBookingInfo(bookingEntity);

        return BookingInfoResponse.builder()
                .bookingId(bookingInfoRequest.getBookingId())
                .accountEmail(bookingInfoRequest.getAccountEmail())
                .cinemaName(bookingInfoRequest.getCinemaName())
                .hallName(bookingInfoRequest.getHallName())
                .movieName(bookingInfoRequest.getMovieName())
                .seatName(bookingInfoRequest.getSeatName())
                .showTime(bookingInfoRequest.getShowTime())
                .seatPrice(bookingInfoRequest.getSeatPrice())
                .totalPrice(bookingInfoRequest.getTotalPrice())
                .build();
    }

}
