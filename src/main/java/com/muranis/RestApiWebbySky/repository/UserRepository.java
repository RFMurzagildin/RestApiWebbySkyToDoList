package com.muranis.RestApiWebbySky.repository;

import com.muranis.RestApiWebbySky.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    Optional<User> findByUsername(@Param(value = "username")String username);
}
