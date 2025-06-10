package com.muranis.RestApiWebbySky.service;

import com.muranis.RestApiWebbySky.model.Role;
import com.muranis.RestApiWebbySky.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository.findByName("ROLE_USER").get();
    }
}
