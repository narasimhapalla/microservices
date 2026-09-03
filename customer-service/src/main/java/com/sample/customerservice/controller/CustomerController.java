package com.sample.customerservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.customerservice.dto.CustomerRequest;
import com.sample.customerservice.dto.CustomerResponse;
import com.sample.customerservice.model.Customer;
import com.sample.customerservice.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @GetMapping("/count")
    public String getCustoemrCount() {
        return customerService.getCustomers().size()+" customers found";
    }
    
    @PostMapping
public CustomerResponse createCustomer(
        @Valid @RequestBody CustomerRequest request) {

    return customerService.createCustomer(request);
}
}
