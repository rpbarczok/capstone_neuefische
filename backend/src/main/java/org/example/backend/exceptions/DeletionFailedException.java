package org.example.backend.exceptions;

public class DeletionFailedException extends RuntimeException {
    public DeletionFailedException(String message, String entity, int id) {
        super("Deletion of " + entity + " with id " + id + " failed: " + message);
    }
}
