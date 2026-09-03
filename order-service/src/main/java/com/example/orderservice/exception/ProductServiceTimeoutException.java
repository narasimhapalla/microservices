package com.example.orderservice.exception;

public class ProductServiceTimeoutException extends RuntimeException {

    public ProductServiceTimeoutException() {
        super("Product Service request timed out");
    }
}