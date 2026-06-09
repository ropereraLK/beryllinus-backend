package com.beryllinus.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class TimeUtilConfiguration {

    @Bean
    @Primary
    public Clock clock() {
        return Clock.system(ZoneId.of("Asia/Colombo"));
    }
}
