package com.muranis.RestApiWebbySky.controller;

import com.muranis.RestApiWebbySky.model.User;
import com.muranis.RestApiWebbySky.service.UserService;
import com.muranis.RestApiWebbySky.utils.JwtUtil;
import com.muranis.RestApiWebbySky.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            if (userService.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
            }
//            if (!Validation.isValidPassword(user.getPassword())) {
//                return ResponseEntity.badRequest().body(Map.of("error", "Password does not meet requirements"));
//            }

            User registeredUser = userService.registerUser(user);

            // Генерация JWT
            String token = JwtUtil.generateToken(registeredUser.getUsername());

            Map<String, Object> response = new HashMap<>();
            response.put("id", registeredUser.getId());
            response.put("username", registeredUser.getUsername());
            response.put("message", "User registered successfully");
            response.put("token", token); // Добавляем токен в ответ

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An error occurred"));
        }
    }

}
