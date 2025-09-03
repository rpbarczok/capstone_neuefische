package org.example.backend.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadRequestExceptionTest {

    @Test
    void shouldSetExceptionMessage() {
        BadRequestException exception = new BadRequestException("TestMessage");
        assertEquals("Bad request: TestMessage", exception.getMessage());
    }
}