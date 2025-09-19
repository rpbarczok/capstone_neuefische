package org.example.backend.exceptions;

public class ImATeapotException extends RuntimeException {
    public ImATeapotException(String beverage) {
        super("I am a teapot,I can't make " + beverage + "!");
    }
}
