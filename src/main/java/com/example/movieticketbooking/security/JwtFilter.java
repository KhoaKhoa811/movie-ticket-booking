package com.example.movieticketbooking.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final String[] PUBLIC_ENDPOINTS = {"/api/v1/auth", "/api/v1/email"};

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        // return if the requested path is the public endpoint
        for (String endpoint : PUBLIC_ENDPOINTS) {
            System.out.println("endpoint: " + endpoint);
             System.out.println("requestPath: " + requestPath);
             System.out.println(requestPath.startsWith(endpoint));
            if (requestPath.startsWith(endpoint)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        // validate token
        String token = jwtUtils.extractToken(request);
        if (jwtUtils.isValidToken(token)) {
            Authentication authentication = jwtUtils.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }


}
