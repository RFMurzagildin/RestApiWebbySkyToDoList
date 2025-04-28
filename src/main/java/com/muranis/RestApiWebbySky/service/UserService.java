package com.muranis.RestApiWebbySky.service;

import com.muranis.RestApiWebbySky.model.User;
import com.muranis.RestApiWebbySky.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user){
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        return userRepository.save(user);
    }
}
