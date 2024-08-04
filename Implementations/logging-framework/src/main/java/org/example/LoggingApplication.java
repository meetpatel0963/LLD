package org.example;

import org.example.model.LogLevel;
import org.example.service.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LoggingApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(LoggingApplication.class, args);

        // Get logger instance from Spring context
        Logger logger = context.getBean(Logger.class);

        // Use the logger
        logger.log(LogLevel.INFO, "Application started.");
        logger.log(LogLevel.DEBUG, "Debugging information.");
        logger.log(LogLevel.WARNING, "Warning: Disk space low.");
        logger.log(LogLevel.ERROR, "Error: Database connection failed.");
        logger.log(LogLevel.FATAL, "Fatal error: Server crashed.");
    }

}
