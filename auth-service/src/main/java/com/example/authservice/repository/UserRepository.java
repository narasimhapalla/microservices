package com.example.authservice.repository;

import java.util.Optional;

import com.example.authservice.model.User;

public interface UserRepository {

    Optional<User> findByUsername(String username);
}