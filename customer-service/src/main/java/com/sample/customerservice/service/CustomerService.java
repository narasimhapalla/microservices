package com.sample.customerservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sample.customerservice.dto.CustomerRequest;
import com.sample.customerservice.dto.CustomerResponse;
import com.sample.customerservice.exception.CustomerNotFoundException;
import com.sample.customerservice.model.Customer;
import com.sample.customerservice.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() ->
                    new CustomerNotFoundException(id));
    }

    public CustomerResponse createCustomer(CustomerRequest request) {

    Customer customer = new Customer();

    customer.setName(request.getName());
    customer.setEmail(request.getEmail());

    Customer savedCustomer = customerRepository.save(customer);

    return new CustomerResponse(
            savedCustomer.getId(),
            savedCustomer.getName(),
            savedCustomer.getEmail()
    );
}
}
