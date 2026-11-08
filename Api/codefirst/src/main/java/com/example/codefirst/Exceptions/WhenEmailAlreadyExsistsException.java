package com.example.codefirst.Exceptions;

public class WhenEmailAlreadyExsistsException extends RuntimeException {
    public WhenEmailAlreadyExsistsException(String message) {
        super(message);
    }
    
}
