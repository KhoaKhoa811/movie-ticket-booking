package com.example.movieticketbooking.enums;

public enum BookingStatus {
    PENDING,     // Đang chờ thanh toán
    CONFIRMED,   // Đã thanh toán thành công
    CANCELED,    // Đã hủy đặt vé
    FAILED,      // Thanh toán thất bại
    REFUNDED,    // Đã hoàn tiền
    CHECKED_IN   // Khách hàng đã check-in vào rạp
}
