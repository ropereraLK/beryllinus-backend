package com.beryllinus.backend.debug;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class DebugController {

    private final ApplicationContext context;

    public DebugController(ApplicationContext context) {
        this.context = context;
    }

    @Value("${spring.flyway.enabled:false}")
    private boolean flywayEnabled;

    @GetMapping("/debug")
    public String debug() {
        return "flyway.enabled=" + flywayEnabled;
    }

    @GetMapping("/debug/beans")
    public List<String> beans() {
        return Arrays.stream(context.getBeanDefinitionNames())
                .filter(name -> name.toLowerCase().contains("flyway"))
                .sorted()
                .toList();
    }
}