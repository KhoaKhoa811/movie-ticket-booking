package com.example.movieticketbooking.dto.pagination;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDir;
}
