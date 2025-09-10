package org.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalIdInputDto {
    private int id;
    private String name;
    private String birthDate;
    private String species;
    private String gender;
}
