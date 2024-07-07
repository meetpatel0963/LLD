package DesignPatterns;

public class ChainOfResponsibilityPattern {
    /*
    * It is used in cases where we receive a request from a client, and we have a chain of handlers to handle
    * any given request. We don't know which handler will handler/s a given request.
    * Example: Design ATM, Vending Machine, Logger
    * In case of an ATM: we can consider a chain of handlers each referring to currency denomination, meaning handlers
    * for notes of Rs. 2000, 500, 100, 50, etc.
    * Now, given a request to withdraw some amount X, the request goes through this chain of handlers to extract
    * the given amount.
    * */

    /*
    * Design a Logger
    * Chain of Responsibility (COR) pattern makes sense to design a logger since in loggers like log4j we have
    * different levels of logging with certain order: ALL < DEBUG < INFO < WARN < ERROR < FATAL < OFF
    * In such cases, maintaining an if-else ladder along with multiple objects for different levels of logging can be
    * cumbersome. COR pattern keeps it clean and maintainable.
     * */
    enum LogLevel {
        INFO,
        DEBUG,
        ERROR
    }

    abstract static class LogProcessor {
        private final LogProcessor nextLogProcessor;

        public LogProcessor(LogProcessor logProcessor) {
            this.nextLogProcessor = logProcessor;
        }

        public void log(LogLevel logLevel, String message) {
            if (nextLogProcessor != null) {
                nextLogProcessor.log(logLevel, message);
            }
        }
    }

    static class InfoLogProcessor extends LogProcessor {
        public InfoLogProcessor(LogProcessor nextLogProcessor) {
            super(nextLogProcessor);
        }

        public void log(LogLevel logLevel, String message) {
            if (logLevel.equals(LogLevel.INFO)) {
                System.out.println("INFO: " + message);
            } else {
                super.log(logLevel, message);
            }
        }
    }

    static class DebugLogProcessor extends LogProcessor {
        public DebugLogProcessor(LogProcessor nextLogProcessor) {
            super(nextLogProcessor);
        }

        public void log(LogLevel logLevel, String message) {
            if (logLevel.equals(LogLevel.DEBUG)) {
                System.out.println("DEBUG: " + message);
            } else {
                super.log(logLevel, message);
            }
        }
    }

    static class ErrorLogProcessor extends LogProcessor {
        public ErrorLogProcessor(LogProcessor nextLogProcessor) {
            super(nextLogProcessor);
        }

        public void log(LogLevel logLevel, String message) {
            if (logLevel.equals(LogLevel.ERROR)) {
                System.out.println("ERROR: " + message);
            } else {
                super.log(logLevel, message);
            }
        }
    }

    public static void main(String[] args) {
        LogProcessor LOGGER = new DebugLogProcessor(new InfoLogProcessor(new ErrorLogProcessor(null)));

        LOGGER.log(LogLevel.ERROR, "Error message");
        LOGGER.log(LogLevel.INFO, "Info message");
        LOGGER.log(LogLevel.DEBUG, "Debug message");
    }
}
