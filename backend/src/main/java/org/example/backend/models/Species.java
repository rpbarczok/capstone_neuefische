package org.example.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class Species {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Getter
    private String genus;

    public Species(String genus) {
        this.genus=genus;
    }

    public Species(Integer id, String genus) {
        this.genus=genus;
        this.Id=id;
    }

}
