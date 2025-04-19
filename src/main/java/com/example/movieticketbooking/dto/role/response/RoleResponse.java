package com.example.movieticketbooking.dto.role.response;

import com.example.movieticketbooking.dto.permission.response.PermissionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse {
    private Integer id;
    private String name;
    private List<PermissionResponse> permissions;
}
