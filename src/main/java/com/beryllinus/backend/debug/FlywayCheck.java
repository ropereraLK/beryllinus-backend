package com.beryllinus.backend.debug;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FlywayCheck {

    public FlywayCheck(ApplicationContext context) {

        System.out.println(
                "Flyway bean exists = "
                        + context.containsBean("flyway")
        );

        System.out.println(
                "Flyway beans = "
                        + Arrays.toString(
                        context.getBeanNamesForType(
                                org.flywaydb.core.Flyway.class
                        )
                )
        );
    }
}