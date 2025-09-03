package org.example.backend.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super("Bad request: " + message);
    }
}
