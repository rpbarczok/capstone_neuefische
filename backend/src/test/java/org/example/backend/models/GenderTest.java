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

    @Test
    void getGenderStringFromGender_shouldReturnMaennlich() {
        assertEquals("männlich", Gender.getGenderStringFromGender("MALE"));
    }

    @Test
    void getGenderStringFromGender_shouldReturnWeiblich() {
        assertEquals("weiblich", Gender.getGenderStringFromGender("FEMALE"));
    }

    @Test
    void getGenderStringFromGender_shouldReturnHermaphrodite() {
        assertEquals("zweigeschlechtlich", Gender.getGenderStringFromGender("HERMAPHRODITE"));
    }

    @Test
    void getGenderStringFromGender_shouldReturnUnknownForOther() {
        assertEquals("unbekannt", Gender.getGenderStringFromGender("divers"));
        assertEquals("unbekannt", Gender.getGenderStringFromGender(""));
    }


}