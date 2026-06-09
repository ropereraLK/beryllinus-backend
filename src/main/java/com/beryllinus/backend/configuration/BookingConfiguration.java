package com.beryllinus.backend.configuration;

import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "booking")
public class BookingConfiguration {

    @Min(1)
    private int maxSearchDaysAhead;

    @Min(1)
    private int maxStayLength;
}