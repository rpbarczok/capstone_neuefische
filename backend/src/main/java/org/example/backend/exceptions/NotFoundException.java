package org.example.backend.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, int id) {

        super(entity + " with id " + id + " not found");
    }
}
