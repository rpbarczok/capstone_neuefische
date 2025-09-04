package org.example.backend.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameNotFoundExceptionTest {

    @Test
    void shouldSetExceptionMessage() {
        NameNotFoundException exception = new NameNotFoundException("Test", "Test");
        assertEquals("Test Test not found", exception.getMessage());
    }
}