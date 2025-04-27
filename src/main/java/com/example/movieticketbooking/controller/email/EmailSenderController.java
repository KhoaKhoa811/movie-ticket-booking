package com.example.movieticketbooking.controller.email;

import com.example.movieticketbooking.dto.api.ApiResponse;
import com.example.movieticketbooking.dto.auth.request.VerifyEmailRequest;
import com.example.movieticketbooking.enums.Code;
import com.example.movieticketbooking.service.email.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/email")
@RequiredArgsConstructor
public class EmailSenderController {
    private final EmailSenderService emailSenderService;

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> sendMail(@RequestBody VerifyEmailRequest verifyEmailRequest){
        emailSenderService.sendVerificationEmail(verifyEmailRequest);
        ApiResponse<?> response = ApiResponse.builder()
                .code(Code.EMAIL_SENT_SUCCESS.getCode())
                .message(Code.EMAIL_SENT_SUCCESS.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }


}
