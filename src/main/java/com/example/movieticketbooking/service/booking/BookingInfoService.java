package com.example.movieticketbooking.service.booking;

import com.example.movieticketbooking.dto.booking.request.BookingInfoRequest;
import com.example.movieticketbooking.entity.BookingEntity;

public interface BookingInfoService {
    BookingInfoRequest getBookingInfo(BookingEntity bookingEntity);
}
