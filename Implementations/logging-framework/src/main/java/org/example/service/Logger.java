package org.example.service;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.example.config.LoggerConfig;
import org.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class Logger implements LogObservable {
    @Autowired
    private LoggerConfig loggingConfig;

    @Setter private LogLevel currentLogLevel;
    private final List<LogObserver> observers = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();

    @PostConstruct
    private void init() {
        this.currentLogLevel = loggingConfig.getLevel();
        List<LogObserver> destinations = createLogObservers(loggingConfig.getDestinations());
        for (LogObserver destination : destinations) {
            addLogObserver(destination);
        }
    }

    private List<LogObserver> createLogObservers(List<String> destinationConfigs) {
        List<LogObserver> destinations = new ArrayList<>();
        for (String destinationConfig : destinationConfigs) {
            if (destinationConfig.equalsIgnoreCase("console")) {
                destinations.add(new ConsoleLogObserver());
            } else if (destinationConfig.startsWith("file:")) {
                String filePath = destinationConfig.substring("file:".length());
                destinations.add(new FileLogObserver(filePath));
            }
            // Add more conditions for other destination types as needed
        }
        return destinations;
    }

    public void log(LogLevel level, String message) {
        if (level.getPriority() >= currentLogLevel.getPriority()) {
            LogMessage logMessage = new LogMessage(getLogMessageId(), level, message);
            notifyLogObservers(logMessage);
        }
    }

    private String getLogMessageId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addLogObserver(LogObserver observer) {
        lock.lock();
        try {
            this.observers.add(observer);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void removeLogObserver(LogObserver observer) {
        lock.lock();
        try {
            this.observers.remove(observer);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void notifyLogObservers(LogMessage logMessage) {
        lock.lock();
        try {
            for (LogObserver observer : observers) {
                observer.write(logMessage);
            }
        } finally {
            lock.unlock();
        }
    }
}
