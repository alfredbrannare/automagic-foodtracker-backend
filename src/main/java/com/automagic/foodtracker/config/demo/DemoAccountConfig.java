package com.automagic.foodtracker.config.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.demo")
public class DemoAccountConfig {
    private boolean enabled = false;
    private String username = "demo";
    private String password = "demo123";
    private String email = "demo@example.com";
}
