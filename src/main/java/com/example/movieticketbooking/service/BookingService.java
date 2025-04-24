package com.example.movieticketbooking.service;

import com.example.movieticketbooking.dto.booking.request.BookingRequest;
import com.example.movieticketbooking.dto.booking.request.ConfirmPaymentRequest;
import com.example.movieticketbooking.dto.booking.response.BookingResponse;

import java.util.List;

public interface BookingService {
    List<Integer> reserveTickets(BookingRequest request);
    BookingResponse createBooking(BookingRequest request);
    BookingResponse confirmPayment(ConfirmPaymentRequest request);
    void cancelExpiredBooking(Integer bookingId);
    void releaseTicketByTicketId(Integer ticketId);
}
