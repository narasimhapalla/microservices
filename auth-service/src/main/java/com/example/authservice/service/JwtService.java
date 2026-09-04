package com.example.authservice.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwtService {

    private final String jwtSecret;

    public JwtService(
            @Value("${jwt.secret}") String jwtSecret) {

        this.jwtSecret = jwtSecret;
    }

    public String generateToken(
            String username,
            String role) throws Exception {

        JWTClaimsSet claims =
                new JWTClaimsSet.Builder()
                .issuer("auth-service")
                        .subject(username)
                        .claim("role", role)
                        .issueTime(new Date())
                        .expirationTime(
                                new Date(
                                        System.currentTimeMillis()
                                                + 60 * 60 * 1000
                                )
                        )
                        .build();

        SignedJWT jwt =
                new SignedJWT(
                        new JWSHeader(JWSAlgorithm.HS256),
                        claims
                );

        SecretKeySpec secretKey =
                new SecretKeySpec(
                        jwtSecret.getBytes(StandardCharsets.UTF_8),
                        "HmacSHA256"
                );

        jwt.sign(new MACSigner(secretKey));

        return jwt.serialize();
    }
}