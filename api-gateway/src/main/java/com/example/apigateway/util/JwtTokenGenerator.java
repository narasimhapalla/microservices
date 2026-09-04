package com.example.apigateway.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class JwtTokenGenerator {

    public static void main(String[] args) throws Exception {

        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                    "JWT_SECRET environment variable is not set"
            );
        }

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject("john")
                .claim("role", "USER")
                .issueTime(new Date())
                .expirationTime(
                        new Date(
                                System.currentTimeMillis()
                                        + 60 * 60 * 1000
                        )
                )
                .build();

        SignedJWT jwt = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claims
        );

        jwt.sign(
                new MACSigner(
                        secret.getBytes(StandardCharsets.UTF_8)
                )
        );

        System.out.println(jwt.serialize());
    }
}
