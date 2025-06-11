package com.muranis.RestApiWebbySky.controller;

import com.muranis.RestApiWebbySky.service.UserService;
import com.muranis.RestApiWebbySky.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidateTokenController {

    private final JwtTokenUtils jwtTokenUtils;
    private final UserService userService;

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        return userService.validateToken(authorizationHeader);
    }
}