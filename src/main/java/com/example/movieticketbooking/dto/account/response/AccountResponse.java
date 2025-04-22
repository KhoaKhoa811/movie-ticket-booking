package com.example.movieticketbooking.dto.account.response;

import com.example.movieticketbooking.dto.role.response.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private Integer id;
    private String email;
    private Boolean enabled;
    private List<RoleResponse> roles;
}
