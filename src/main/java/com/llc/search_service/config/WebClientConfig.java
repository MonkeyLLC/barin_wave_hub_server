package com.llc.search_service.config;

import org.springframework.web.client.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {

    /*@Bean
    public RestClient restClient() {
        return RestClient.builder().build();
    }*/

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
