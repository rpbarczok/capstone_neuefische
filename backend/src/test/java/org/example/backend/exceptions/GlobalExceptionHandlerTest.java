package org.example.backend.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleInternalServerException_returnsInternalServerErrorMessage() {
        String result = handler.handleInternalServerException();
        assertEquals("Internal Server Error", result);
    }

    @Test
    void handleNotFoundException_returnsMessage() {
        NotFoundException ex = new NotFoundException("Species", 1);
        String result = handler.handleNotFoundException(ex);
        assertEquals("Species with id 1 not found", result);
    }

    @Test
    void handleNameNotFoundException_returnsMessage() {
        NameNotFoundException ex = new NameNotFoundException("Species", "Test");
        String result = handler.handleNameNotFoundException(ex);
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

    @Test
    void handleDeletionFailedException_returnsMessage() {
        DeletionFailedException ex = new DeletionFailedException("animal with id 1 not found", "animal", 1);
        String result = handler.handleDeletionFailedException(ex);
        assertEquals("Deletion of animal with id 1 failed: animal with id 1 not found", result);
    }

    @Test
    void handleImATeapotException_returnsMessage() {
        ImATeapotException ex = new ImATeapotException("coffee");
        String result = handler.handleImATeapotException(ex);
        assertEquals("I am a teapot,I can't make coffee!", result);
    }

    @Test
    void handleTeaNotFoundException_returnsMessage() {
        TeaNotFoundException ex = new TeaNotFoundException();
        String result = handler.handleTeaNotFoundException(ex);
        assertEquals("O dear, I haven't found any tea, unfortunately I ran out. Sorry.", result);
    }
}