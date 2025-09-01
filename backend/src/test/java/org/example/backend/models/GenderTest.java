package org.example.backend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderTest {

    @Test
    void getGenderFromString_shouldReturnMale() {
        assertEquals(Gender.MALE, Gender.getGenderFromString("männlich"));
    }

    @Test
    void getGenderFromString_shouldReturnFemale() {
        assertEquals(Gender.FEMALE, Gender.getGenderFromString("weiblich"));
    }

    @Test
    void getGenderFromString_shouldReturnHermaphrodite() {
        assertEquals(Gender.HERMAPHRODITE, Gender.getGenderFromString("zweigeschlechtlich"));
    }

    @Test
    void getGenderFromString_shouldReturnUnknownForOther() {
        assertEquals(Gender.UNKNOWN, Gender.getGenderFromString("divers"));
        assertEquals(Gender.UNKNOWN, Gender.getGenderFromString(""));
    }


}