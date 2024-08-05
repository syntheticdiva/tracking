package com.example.tracking.exception;

public class MailItemNotFoundException extends RuntimeException {
    public MailItemNotFoundException(String message) {
        super(message);
    }
}