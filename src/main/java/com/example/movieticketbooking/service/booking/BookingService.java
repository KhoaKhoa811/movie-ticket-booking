package com.example.movieticketbooking.service.booking;

import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.booking.request.BookingRequest;
import com.example.movieticketbooking.dto.booking.request.ConfirmPaymentRequest;
import com.example.movieticketbooking.dto.booking.response.BookingInfoResponse;
import com.example.movieticketbooking.dto.booking.response.BookingResponse;
import org.springframework.data.domain.Pageable;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    BookingResponse confirmPayment(ConfirmPaymentRequest request);
    PagedResponse<BookingInfoResponse> getAllBooking(Pageable pageable);
}
