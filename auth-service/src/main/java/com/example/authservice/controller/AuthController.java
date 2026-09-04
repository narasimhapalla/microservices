package com.example.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authservice.model.LoginRequest;
import com.example.authservice.model.LoginResponse;
import com.example.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request)
            throws Exception {
System.out.println(
            ">>> AuthController.login() called: "
            + request.getUsername()
    );
        String token =
                authService.authenticate(
                        request.getUsername(),
                        request.getPassword()
                );

        return new LoginResponse(token);
    }
}