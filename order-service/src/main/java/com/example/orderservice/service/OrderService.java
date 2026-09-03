package com.example.orderservice.service;

import com.example.orderservice.client.CustomerClient;
import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.CustomerResponse;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.ProductResponse;
import com.example.orderservice.model.CustomerOrder;
import com.example.orderservice.repository.CustomerOrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final CustomerOrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;

    public OrderService(
            CustomerOrderRepository orderRepository,
            CustomerClient customerClient,
            ProductClient productClient) {

        this.orderRepository = orderRepository;
        this.customerClient = customerClient;
        this.productClient = productClient;
    }

    public CustomerOrder createOrder(OrderRequest request) {

        // 1. Validate customer
        CustomerResponse customer =
                customerClient.getCustomer(
                        request.getCustomerId()
                );

        // 2. Validate product
        ProductResponse product =
                productClient.getProduct(
                        request.getProductId()
                );

        // 3. Calculate total
        BigDecimal totalPrice =
                product.getPrice()
                        .multiply(
                                BigDecimal.valueOf(
                                        request.getQuantity()
                                )
                        );

        // 4. Create order
        CustomerOrder order =
                new CustomerOrder(
                        customer.getId(),
                        product.getId(),
                        request.getQuantity(),
                        totalPrice
                );

        // 5. Save order
        return orderRepository.save(order);
    }
}