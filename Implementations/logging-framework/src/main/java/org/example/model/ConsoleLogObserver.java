package org.example.model;

public class ConsoleLogObserver implements LogObserver {
    @Override
    public void write(LogMessage message) {
        System.out.println("Writing log message to console: " + message.format());
    }
}
