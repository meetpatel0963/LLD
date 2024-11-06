package com.example.tollservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "pass")
public class PassConfig {

    private PriceDetails oneWay;
    private PriceDetails roundTrip;
    private PriceDetails daily;
    private PriceDetails weekly;

    @Setter
    @Getter
    public static class PriceDetails {
        private double price;
        private Duration validity;
    }
}
