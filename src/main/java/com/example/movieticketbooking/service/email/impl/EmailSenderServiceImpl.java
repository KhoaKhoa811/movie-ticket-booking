package com.example.movieticketbooking.service.email.impl;

import com.example.movieticketbooking.dto.auth.request.VerifyEmailRequest;
import com.example.movieticketbooking.dto.booking.request.BookingInfoRequest;
import com.example.movieticketbooking.service.email.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSenderImpl mailSender;
    @Value( "${app.verify-link}")
    private String verifyLink;
    @Value( "${app.booking-email-subject}")
    private String bookingSubject;

    @Override
    public String loadEmailTemplate(String path, Map<String, String> replacements) throws IOException {
        Resource resource = new ClassPathResource(path);
        String content = Files.readString(resource.getFile().toPath());
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            content = content.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return content;
    }

    @Override
    @Async("taskExecutor")
    public void sendVerificationEmail(VerifyEmailRequest verifyEmailRequest) {

        Map<String, String> replacements = new HashMap<>();
        replacements.put("verifyLink", verifyEmailRequest.getVerificationLink());

        try {
            String htmlContent = loadEmailTemplate("templates/verify-email.html", replacements);
            sendHtmlEmail(verifyEmailRequest.getEmail(), verifyEmailRequest.getSubject(), htmlContent);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc template: " + e.getMessage());
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = gửi HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email HTML: " + e.getMessage());
        }
    }

    @Override
    @Async("taskExecutor")
    public void sendBookingEmail(BookingInfoRequest bookingInfoEmailRequest) {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("bookingId", bookingInfoEmailRequest.getBookingId().toString());
        replacements.put("movieName", bookingInfoEmailRequest.getMovieName());
        replacements.put("cinemaName", bookingInfoEmailRequest.getCinemaName());
        replacements.put("hallName", bookingInfoEmailRequest.getHallName());
        replacements.put("showTime", bookingInfoEmailRequest.getShowTime());
        replacements.put("seatName", String.join(", ", bookingInfoEmailRequest.getSeatName()));
        replacements.put("seatPrice", String.join(", ", bookingInfoEmailRequest.getSeatPrice().toString()));
        replacements.put("totalPrice", bookingInfoEmailRequest.getTotalPrice().toString());

        try {
            String htmlContent = loadEmailTemplate("templates/booking-email.html", replacements);
            sendHtmlEmail(bookingInfoEmailRequest.getAccountEmail(), bookingSubject, htmlContent);
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc template: " + e.getMessage());
        }
    }
}
