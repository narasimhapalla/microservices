package com.example.orderservice.exception;

public class CustomerServiceTimeoutException extends RuntimeException {

    public CustomerServiceTimeoutException() {
        super("Customer Service request timed out");
    }
}