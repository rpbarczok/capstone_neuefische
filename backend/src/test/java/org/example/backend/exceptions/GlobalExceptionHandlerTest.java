package org.example.backend.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleInternalServerException_returnsInternalServerErrorMessage() {
        String result = handler.handleInternalServerException(new Exception("fail"));
        assertEquals("Internal Server Error", result);
    }

    @Test
    void handleNotFoundException_returnsMessage() {
        NameNotFoundException ex = new NameNotFoundException("Species", "Test");
        String result = handler.handleNotFoundException(ex);
        assertEquals("Species Test not found", result);
    }

    @Test
    void handleBadRequestException_returnsMessage() {
        BadRequestException ex = new BadRequestException("Bad request!");
        String result = handler.handleBadRequestException(ex);
        assertEquals("Bad request: Bad request!", result);
    }

    @Test
    void handleCreationFailedException_returnsMessage() {
        CreationFailedException ex = new CreationFailedException("animal", "Test");
        String result = handler.handleCreationFailedException(ex);
        assertEquals("Creation of animal Test failed", result);
    }
}