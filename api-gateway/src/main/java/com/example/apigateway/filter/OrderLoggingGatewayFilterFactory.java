package com.example.apigateway.filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderLoggingGatewayFilterFactory
        extends AbstractGatewayFilterFactory<Object> {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    OrderLoggingGatewayFilterFactory.class
            );

    public OrderLoggingGatewayFilterFactory() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {

        return (exchange, chain) -> {

            String correlationId =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst("X-Correlation-ID");

            logger.info(
                    "Order Route Filter: {} {} | CorrelationId: {}",
                    exchange.getRequest().getMethod(),
                    exchange.getRequest().getURI(),
                    correlationId
            );

            return chain.filter(exchange);
        };
    }
}