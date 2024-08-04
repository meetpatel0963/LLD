package org.example.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LogMessage {
    private final String id;
    private final String message;
    private final LogLevel level;
    private final LocalDateTime timestamp;

    public LogMessage(String id, LogLevel level, String message) {
        this.id = id;
        this.level = level;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String format() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s [%s] %s", timestamp.format(formatter), level, message);
    }
}
