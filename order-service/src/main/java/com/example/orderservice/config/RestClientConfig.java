package com.example.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    // 1. This bean is used by Eureka and for external API calls (NOT load-balanced)
    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 2. This bean is used specifically for microservice-to-microservice calls (Load-balanced)
    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    // 3. Standard RestClient.Builder bean
    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    // 4. Standard RestClient bean for CustomerClient (targets localhost:8080)
    @Bean
    public RestClient restClient(RestClient.Builder restClientBuilder) {
        return restClientBuilder.build();
    }

    // 5. Load-balanced RestClient.Builder bean specifically for ProductClient
    @Bean("productServiceRestClientBuilder")
    @LoadBalanced
    public RestClient.Builder productServiceRestClientBuilder() {
        return RestClient.builder();
    }
}
