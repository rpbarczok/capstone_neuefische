package org.example.backend.models;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("männlich"),
    FEMALE("weiblich"),
    HERMAPHRODITE("zweigeschlechtlich"),
    UNKNOWN("unbekannt");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public static Gender getGenderFromString(String value) {
        return switch (value) {
            case "männlich" -> Gender.MALE;
            case "weiblich" -> Gender.FEMALE;
            case "zweigeschlechtlich" -> Gender.HERMAPHRODITE;
            default -> Gender.UNKNOWN;
        };
    }
}
