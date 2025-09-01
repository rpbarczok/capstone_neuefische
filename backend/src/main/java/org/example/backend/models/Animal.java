package org.example.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(nullable=false)
    private String name;

    @Transient
    private Long age;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name="species_id", nullable=false)
    private Species species;

    @Column(nullable = false)
    private Gender gender;

    public Animal(String name, LocalDate birthDate, Species species, Gender gender) {
        this.name = name;
        this.age = ChronoUnit.DAYS.between(birthDate, LocalDate.now());
        this.birthDate = birthDate;
        this.species = species;
        this.gender = gender;
    }

    public Animal(int id, String name, LocalDate birthDate, Species species, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = ChronoUnit.DAYS.between(birthDate, LocalDate.now());
        this.birthDate = birthDate;
        this.species = species;
        this.gender = gender;
    }

}
