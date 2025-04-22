package com.example.movieticketbooking.dto.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserPasswordRequest {
    private String currentPassword;
    private String newPassword;
}
