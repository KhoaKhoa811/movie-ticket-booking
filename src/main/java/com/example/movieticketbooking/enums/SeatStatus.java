package com.example.movieticketbooking.enums;

public enum SeatStatus {
    AVAILABLE,      // Ghế trống, có thể đặt
    RESERVED,       // Đã được giữ chỗ (chưa thanh toán)
    BOOKED,         // Đã đặt thành công (đã thanh toán)
    OCCUPIED,       // Ghế đã có người ngồi
    BLOCKED,        // Ghế bị chặn (không thể đặt, có thể do lỗi hệ thống hoặc sự kiện đặc biệt)
    MAINTENANCE     // Ghế đang bảo trì, không thể sử dụng
}
