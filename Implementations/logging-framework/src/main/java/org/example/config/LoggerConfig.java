package org.example.config;

import lombok.Getter;
import lombok.Setter;
import org.example.model.LogLevel;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "custom-logging")
@Getter
@Setter
public class LoggerConfig {
    private LogLevel level;
    private List<String> destinations;
}
