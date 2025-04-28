package com.muranis.RestApiWebbySky.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String path = request.getRequestURI();

        // Исключаем пути /api/auth/** из проверки токена
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = JwtUtil.validateToken(token); // Проверка токена
                System.out.println(username);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Устанавливает 401 ошибку
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}