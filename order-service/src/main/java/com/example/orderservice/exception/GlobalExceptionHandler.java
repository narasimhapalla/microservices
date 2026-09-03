package com.example.orderservice.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleProductNotFound(
            ProductNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        Map.of(
                                "error",
                                exception.getMessage()
                        )
                );
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCustomerNotFound(
            CustomerNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        Map.of(
                                "error",
                                exception.getMessage()));
    }
    
     @ExceptionHandler(CustomerServiceException.class)
    public ResponseEntity<Map<String, String>> handleCustomerServiceException(
            CustomerServiceException exception) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error",
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(ProductServiceException.class)
    public ResponseEntity<Map<String, String>> handleProductServiceException(
            ProductServiceException exception) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "error",
                        exception.getMessage()));
    }

    @ExceptionHandler(ProductServiceUnavailableException.class)
public ResponseEntity<Map<String, String>>
handleProductServiceUnavailable(
                ProductServiceUnavailableException exception) {

    return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(
                    Map.of(
                            "error",
                            exception.getMessage()));
}
@ExceptionHandler(CustomerServiceUnavailableException.class)
public ResponseEntity<Map<String, String>>
handleCustomerServiceUnavailable(
                CustomerServiceUnavailableException exception) {

    return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(
                    Map.of(
                            "error",
                            exception.getMessage()));
}

@ExceptionHandler(ProductServiceTimeoutException.class)
public ResponseEntity<Map<String, String>>
handleProductServiceTimeout(
                ProductServiceTimeoutException exception) {

    return ResponseEntity
            .status(HttpStatus.GATEWAY_TIMEOUT)
            .body(
                    Map.of(
                            "error",
                            exception.getMessage()));
}

@ExceptionHandler(CustomerServiceTimeoutException.class)
public ResponseEntity<Map<String, String>>
handleCustomerServiceTimeout(
                CustomerServiceTimeoutException exception) {

    return ResponseEntity
            .status(HttpStatus.GATEWAY_TIMEOUT)
            .body(
                    Map.of(
                            "error",
                            exception.getMessage()));
}
    
@ExceptionHandler(ProductServiceCircuitOpenException.class)
public ResponseEntity<Map<String, String>>
handleProductServiceCircuitOpen(
        ProductServiceCircuitOpenException exception) {

    return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(
                    Map.of(
                            "error",
                            exception.getMessage()
                    )
            );
}
}