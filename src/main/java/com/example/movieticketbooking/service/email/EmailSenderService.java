package com.example.movieticketbooking.service.email;

import java.io.IOException;
import java.util.Map;

public interface EmailSenderService {
    String loadEmailTemplate(String path, Map<String, String> replacements) throws IOException;
    void sendVerificationEmail(String toEmail, String token);
    void sendHtmlEmail(String to, String subject, String htmlContent);
}
