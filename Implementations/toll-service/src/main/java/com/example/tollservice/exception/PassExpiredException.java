package com.example.tollservice.exception;

public class PassExpiredException extends RuntimeException {
    public PassExpiredException(String message) {
        super(message);
    }
}
