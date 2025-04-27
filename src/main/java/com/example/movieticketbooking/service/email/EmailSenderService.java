package com.example.movieticketbooking.service.email;

import com.example.movieticketbooking.dto.auth.request.VerifyEmailRequest;
import com.example.movieticketbooking.dto.booking.request.BookingInfoRequest;

import java.io.IOException;
import java.util.Map;

public interface EmailSenderService {
    String loadEmailTemplate(String path, Map<String, String> replacements) throws IOException;
    void sendVerificationEmail(VerifyEmailRequest verifyEmailRequest);
    void sendHtmlEmail(String to, String subject, String htmlContent);
    void sendBookingEmail(BookingInfoRequest bookingInfoEmailRequest);
}
