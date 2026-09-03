package com.example.orderservice.client;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import com.example.orderservice.dto.CustomerResponse;
import com.example.orderservice.exception.CustomerNotFoundException;
import com.example.orderservice.exception.CustomerServiceException;
import com.example.orderservice.exception.CustomerServiceUnavailableException;

@Component
public class CustomerClient {

    private final RestClient restClient;

    public CustomerClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public CustomerResponse getCustomer(Long customerId) {

        try {
           return restClient
                .get()
                .uri(
                    "http://localhost:8080/customers/{id}",
                    customerId
                )
                   .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            throw new CustomerNotFoundException(customerId);
                        }
                )
                .body(CustomerResponse.class); 
       } catch (ResourceAccessException exception) {

           throw new CustomerServiceUnavailableException();
       }
        catch (CustomerNotFoundException e) {
            throw new CustomerServiceException(
                    "Unable to retrieve customer: "
                            + customerId
            );
        }
        
    }
}