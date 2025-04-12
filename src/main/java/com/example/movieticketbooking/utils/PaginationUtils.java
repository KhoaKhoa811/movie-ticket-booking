package com.example.movieticketbooking.utils;

import com.example.movieticketbooking.config.PaginationProperties;
import com.example.movieticketbooking.dto.pagination.PaginationRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtils {
    public static Pageable createPageable(
            PaginationRequest request,
            PaginationProperties paginationProperties
    ) {
        int actualPage = request.getPage() != null ? request.getPage() : paginationProperties.getDefaultPage();
        int actualSize = request.getSize() != null ? request.getSize() : paginationProperties.getDefaultSize();
        String actualSortBy = request.getSortBy() != null ? request.getSortBy() : paginationProperties.getDefaultSortBy();
        String actualSortDir = request.getSortDir() != null ? request.getSortDir() : paginationProperties.getDefaultSortDir();

        Sort sort = actualSortDir.equalsIgnoreCase("desc") ?
                Sort.by(actualSortBy).descending() :
                Sort.by(actualSortBy).ascending();

        return PageRequest.of(actualPage, actualSize, sort);
    }
}
