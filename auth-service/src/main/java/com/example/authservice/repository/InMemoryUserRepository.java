package com.example.authservice.repository;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.authservice.model.User;

@Repository
public class InMemoryUserRepository
        implements UserRepository {

 private final Map<String, User> users;

    public InMemoryUserRepository(
            PasswordEncoder passwordEncoder) {

        users = Map.of(
                "john",
                new User(
                        "john",
                        passwordEncoder.encode("password"),
                        "USER"
                ),

                "palla",
                new User(
                        "alice",
                        passwordEncoder.encode("narasimha"),
                        "ADMIN"
                )
        );
    }

    @Override
    public Optional<User> findByUsername(
            String username) {

        return Optional.ofNullable(
                users.get(username)
        );
    }
}