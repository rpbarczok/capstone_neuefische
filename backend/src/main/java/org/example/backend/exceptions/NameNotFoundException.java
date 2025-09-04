package org.example.backend.exceptions;

public class NameNotFoundException extends RuntimeException {

    public NameNotFoundException(String entity, String name) {
        super(entity +" " + name + " not found");
    }
}
