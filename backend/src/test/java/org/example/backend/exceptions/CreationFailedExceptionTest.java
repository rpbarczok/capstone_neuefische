package org.example.backend.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreationFailedExceptionTest {

    @Test
    void shouldSetExceptionMessage() {
        CreationFailedException exception = new CreationFailedException("Test", "Test");
        assertEquals("Creation of Test Test failed", exception.getMessage());
    }
}