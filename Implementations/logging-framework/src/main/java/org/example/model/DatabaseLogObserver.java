package org.example.model;

import lombok.AllArgsConstructor;

import javax.sql.DataSource;

@AllArgsConstructor
public class DatabaseLogObserver implements LogObserver {
    private final DataSource dataSource;

    @Override
    public void write(LogMessage message) {
        System.out.println("Writing log message to database: " + message.format());
    }
}
