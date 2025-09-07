package org.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalIdOutputDto {
    private int id;
    private String name;
    private String birthDate;
    private Long age;
    private String species;
    private String gender;

}
