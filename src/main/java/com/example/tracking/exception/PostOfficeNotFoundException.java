package com.example.tracking.exception;

public class PostOfficeNotFoundException extends RuntimeException {
    public PostOfficeNotFoundException(String message) {
        super(message);
    }
}