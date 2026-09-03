package com.example.orderservice.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID =
            "X-Correlation-ID";

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        String correlationId =
                httpRequest.getHeader(CORRELATION_ID);

        if (correlationId == null
                || correlationId.isBlank()) {

            correlationId =
                    java.util.UUID.randomUUID()
                            .toString();
        }

        try {

            MDC.put(
                    CORRELATION_ID,
                    correlationId
            );

            chain.doFilter(request, response);

        } finally {

            MDC.remove(CORRELATION_ID);
        }
    }
}