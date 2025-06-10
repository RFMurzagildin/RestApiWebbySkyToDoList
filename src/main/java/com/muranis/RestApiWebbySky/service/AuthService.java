package com.muranis.RestApiWebbySky.service;

import com.muranis.RestApiWebbySky.dto.JwtRequest;
import com.muranis.RestApiWebbySky.dto.RegistrationUserDto;
import com.muranis.RestApiWebbySky.exceptions.ApiResponse;
import com.muranis.RestApiWebbySky.utils.JwtTokenUtils;
import com.muranis.RestApiWebbySky.utils.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(new ApiResponse(false, "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return new ResponseEntity<>(new ApiResponse(true, token), HttpStatus.OK);
    }


    public ResponseEntity<?> createNewUser(RegistrationUserDto registrationUserDto) {
        if (!Validation.isValidUsername(registrationUserDto.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid username"), HttpStatus.BAD_REQUEST);
        }

        if (!Validation.isValidPassword(registrationUserDto.getPassword())) {
            return new ResponseEntity<>(new ApiResponse(false, "Invalid password"), HttpStatus.BAD_REQUEST);
        }

        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new ApiResponse(false, "Passwords don't match"), HttpStatus.BAD_REQUEST);
        }

        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "The user with the specified name already exists"), HttpStatus.BAD_REQUEST);
        }

        userService.createNewUser(registrationUserDto);
        return new ResponseEntity<>(new ApiResponse(true, "The user has been successfully registered"), HttpStatus.OK);
    }
}
