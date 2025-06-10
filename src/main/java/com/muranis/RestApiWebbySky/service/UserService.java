package com.muranis.RestApiWebbySky.service;

import com.muranis.RestApiWebbySky.dto.RegistrationUserDto;
import com.muranis.RestApiWebbySky.exceptions.ApiResponse;
import com.muranis.RestApiWebbySky.model.User;
import com.muranis.RestApiWebbySky.repository.UserRepository;
import com.muranis.RestApiWebbySky.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%S' не найден", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    public void createNewUser(RegistrationUserDto registrationUserDto){
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        userRepository.save(user);
    }

    public ResponseEntity<?> validateToken(String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                if (jwtTokenUtils.validateToken(token)) {
                    return new ResponseEntity<>(new ApiResponse(true, "True token"), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(new ApiResponse(true, "Invalid or expired token"), HttpStatus.UNAUTHORIZED);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(new ApiResponse(true, "Invalid token"), HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(new ApiResponse(true, "Missing token"), HttpStatus.UNAUTHORIZED);
    }
}
