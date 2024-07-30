package com.meetpatel.io.output;

/**
 * Implements the IOutputPrinter strategy for console based logging.
 * This class prints the messages/logs on the console.
 */
public class SysOutOutputPrinter implements IOutputPrinter {

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

}
