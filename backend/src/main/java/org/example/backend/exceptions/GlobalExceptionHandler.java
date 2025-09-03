package org.example.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInternalServerException() {
        return "Internal Server Error";
    }

    @ExceptionHandler(NameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NameNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CreationFailedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCreationFailedException(CreationFailedException e) {
        return e.getMessage();
    }
}
