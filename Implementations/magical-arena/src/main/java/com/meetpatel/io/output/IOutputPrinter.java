package com.meetpatel.io.output;

/**
 * Defines a strategy for printing logs.
 * Concrete implementations should provide specific printing/logging logic.
 */
public interface IOutputPrinter {
    void printMessage(String message);
}
