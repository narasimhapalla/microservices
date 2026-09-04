package com.example.authservice.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.exception.InvalidCredentialsException;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            JwtService jwtService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(
            String username,
            String password) throws Exception {

        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () -> new InvalidCredentialsException(
                                        "Invalid username or password"
                                )
                        );

        if (!passwordEncoder.matches(
                password,
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }

        return jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );
    }
}