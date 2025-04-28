package com.muranis.RestApiWebbySky.config;

import com.muranis.RestApiWebbySky.utils.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Разрешаем доступ ко всем путям /api/auth/**
                        .anyRequest().authenticated()) // Все остальные запросы требуют аутентификации
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}