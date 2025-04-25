package com.example.movieticketbooking.controller;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.booking.request.BookingRequest;
import com.example.movieticketbooking.dto.booking.request.ConfirmPaymentRequest;
import com.example.movieticketbooking.dto.booking.response.BookingResponse;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.booking.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@RequestBody BookingRequest request) {
        ApiResponse<BookingResponse> createBooking = ApiResponse.<BookingResponse>builder()
                .code(Code.BOOKING_CREATED.getCode())
                .data(bookingService.createBooking(request))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(createBooking);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponse<BookingResponse>> confirmPayment(@RequestBody ConfirmPaymentRequest request) {
        ApiResponse<BookingResponse> confirmPayment = ApiResponse.<BookingResponse>builder()
                .code(Code.CONFIRM_PAYMENT.getCode())
                .data(bookingService.confirmPayment(request))
                .build();
        return ResponseEntity.ok(confirmPayment);
    }
}
