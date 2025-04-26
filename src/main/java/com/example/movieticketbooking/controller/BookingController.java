package com.example.movieticketbooking.controller;

import com.cloudinary.Api;
import com.example.movieticketbooking.config.PaginationProperties;
import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.api.PagedResponse;
import com.example.movieticketbooking.dto.booking.request.BookingRequest;
import com.example.movieticketbooking.dto.booking.request.ConfirmPaymentRequest;
import com.example.movieticketbooking.dto.booking.response.BookingInfoResponse;
import com.example.movieticketbooking.dto.booking.response.BookingResponse;
import com.example.movieticketbooking.dto.pagination.PaginationRequest;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.booking.BookingService;
import com.example.movieticketbooking.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final PaginationProperties paginationProperties;

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

    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<BookingInfoResponse>>> getAllBookings(@ModelAttribute PaginationRequest paginationRequest) {
        Pageable pageable = PaginationUtils.createPageable(paginationRequest, paginationProperties);
        ApiResponse<PagedResponse<BookingInfoResponse>> bookingResponse = ApiResponse.<PagedResponse<BookingInfoResponse>>builder()
                .code(Code.BOOKING_GET_ALL.getCode())
                .data(bookingService.getAllBooking(pageable))
                .build();
        return ResponseEntity.ok(bookingResponse);
    }
}
