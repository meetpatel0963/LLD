package org.example.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FileLogObserver implements LogObserver {
    private final String filePath;

    @Override
    public void write(LogMessage message) {
        System.out.println("Writing log message to file: " + message.format());
    }
}
