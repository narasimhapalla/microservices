package com.example.apigateway.filter;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    LoggingGlobalFilter.class
            );

            private static final String CORRELATION_ID =
            "X-Correlation-ID";
    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String correlationId =
                request.getHeaders()
                        .getFirst(CORRELATION_ID);

        Instant startTime = Instant.now();

        logger.info(
                "Incoming Request: {} {} | CorrelationId: {}",
                request.getMethod(),
                request.getURI(),
                correlationId
        );
        logger.info(
                "Incoming Request: {} {}",
                request.getMethod(),
                request.getURI()
        );

        return chain.filter(exchange)
                .then(
                        Mono.fromRunnable(() -> {

                            Duration duration =
                                    Duration.between(
                                            startTime,
                                            Instant.now()
                                    );

                            logger.info(
                                    "Outgoing Response: {} {} | Status: {} | Time: {} ms",
                                    request.getMethod(),
                                    request.getURI(),
                                    correlationId,
                                    exchange
                                            .getResponse()
                                            .getStatusCode(),
                                    duration.toMillis()
                            );

                        })
                );
    }

    @Override
    public int getOrder() {

        return Ordered.HIGHEST_PRECEDENCE + 1;

    }
}