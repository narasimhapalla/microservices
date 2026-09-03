package com.example.orderservice.exception;

public class ProductServiceCircuitOpenException
        extends RuntimeException {

    public ProductServiceCircuitOpenException() {
        super("Product Service is temporarily unavailable. Please try again later.");
    }
}