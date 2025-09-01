package org.example.backend.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
public class Species {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @Column(nullable=false)
    private String genus;

    public Species(String genus) {
        this.genus=genus;
    }

    public Species(Integer id, String genus) {
        this.genus=genus;
        this.Id=id;
    }

}
