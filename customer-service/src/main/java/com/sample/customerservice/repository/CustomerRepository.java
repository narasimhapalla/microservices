package com.sample.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.customerservice.model.Customer;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {
}