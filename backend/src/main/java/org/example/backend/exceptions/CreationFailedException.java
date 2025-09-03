package org.example.backend.exceptions;

public class CreationFailedException extends RuntimeException {
    public CreationFailedException(String entity, String name) {
        super("Creation of " + entity + " " + name + " failed");
    }
}
