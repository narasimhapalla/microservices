package com.example.apigateway.config;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {

        SecretKeySpec secretKey =
                new SecretKeySpec(
                        jwtSecret.getBytes(),
                        "HmacSHA256"
                );

        return NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http) {

        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();

        jwtAuthenticationConverter
                .setJwtGrantedAuthoritiesConverter(jwt -> {
                    String role = jwt.getClaimAsString("role");

                    if (role == null || role.isBlank()) {
                        return java.util.List.of();
                    }

                    return java.util.List.of(
                            new org.springframework.security.core.authority.SimpleGrantedAuthority(
                                    "ROLE_" + role
                            )
                    );
                });

        ReactiveJwtAuthenticationConverterAdapter converter =
                new ReactiveJwtAuthenticationConverterAdapter(
                        jwtAuthenticationConverter
                );

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeExchange(exchange -> exchange

                        .pathMatchers(HttpMethod.GET, "/products/**")
                        .hasAnyRole("USER", "ADMIN")

                        .pathMatchers(HttpMethod.POST, "/products/**")
                        .hasRole("ADMIN")

                        .pathMatchers(HttpMethod.PUT, "/products/**")
                        .hasRole("ADMIN")

                        .pathMatchers(HttpMethod.DELETE, "/products/**")
                        .hasRole("ADMIN")

                        .anyExchange()
                        .authenticated()
                )

                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(jwt -> jwt
                                        .jwtAuthenticationConverter(
                                                converter
                                        )
                                )
                )

                .build();
    }
}