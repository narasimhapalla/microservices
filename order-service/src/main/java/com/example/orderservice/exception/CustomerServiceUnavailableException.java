package com.example.orderservice.exception;

public class CustomerServiceUnavailableException
        extends RuntimeException {

    public CustomerServiceUnavailableException() {
        super("Customer Service is currently unavailable");
    }
}