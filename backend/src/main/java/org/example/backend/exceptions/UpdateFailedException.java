package org.example.backend.exceptions;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(String entity, int id) {

        super("Update of " + entity + " with id " + id + " failed.");
    }
}
