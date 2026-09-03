package com.example.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.example.orderservice.config.ProductServiceProperties;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.exception.ProductNotFoundException;
import com.example.orderservice.exception.ProductServiceCircuitOpenException;
import com.example.orderservice.exception.ProductServiceTimeoutException;
import com.example.orderservice.exception.ProductServiceUnavailableException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Component
public class ProductClient {

   // @Value("${product.service.url}")
//private String productServiceUrl;
private final RestClient restClient;
private final ProductServiceProperties productServiceProperties;
public ProductClient(
        @Qualifier("productServiceRestClientBuilder")
        RestClient.Builder restClientBuilder,
        ProductServiceProperties productServiceProperties) {

    this.restClient = restClientBuilder.build();
    this.productServiceProperties = productServiceProperties;
}
@CircuitBreaker(
    name = "productServiceCircuitBreaker",
    fallbackMethod = "productServiceFallback"
)
@Retry(name = "productServiceRetry")
    public ProductResponse getProduct(Long productId) {
        System.out.println(
        "Calling Product Service for product: "
                + productId
);
        try {
            
            return restClient
                    .get()
                    .uri(
                            "http://PRODUCT-SERVICE/products/{id}",
                            productId)
                    .retrieve()
                    .onStatus(
                            status -> status.value() == 404,
                            (request, response) -> {
                                throw new ProductNotFoundException(productId);
                            })
                    .body(ProductResponse.class);

        } catch (ProductNotFoundException exception) {
            exception.printStackTrace();
            throw exception;

        } catch (ResourceAccessException exception) {

            exception.printStackTrace();
            if (isTimeout(exception)) {
                throw new ProductServiceTimeoutException();
            }

            throw new ProductServiceUnavailableException();
        }
    }

    private boolean isTimeout(Throwable exception) {

        Throwable cause = exception;

        while (cause != null) {

            if (cause instanceof java.net.SocketTimeoutException) {
                return true;
            }

            cause = cause.getCause();
        }

        return false;
    }

    private ProductResponse productServiceFallback(
        Long productId,
        Throwable throwable) {

            System.out.println(
            "Product Service fallback triggered: "
                    + throwable.getClass().getSimpleName()
    );
    if (throwable instanceof CallNotPermittedException) {

        throw new ProductServiceCircuitOpenException();
    }

    if (throwable instanceof ProductServiceTimeoutException) {

        throw new ProductServiceTimeoutException();
    }

    if (throwable instanceof ProductServiceUnavailableException) {

        throw new ProductServiceUnavailableException();
    }

    throw new ProductServiceUnavailableException();
}

}