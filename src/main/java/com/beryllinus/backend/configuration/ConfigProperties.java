package com.beryllinus.backend.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
