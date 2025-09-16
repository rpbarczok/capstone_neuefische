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

    @Column(nullable=false, unique=true)
    private String genus;

    private String imgUrl;

    public Species(String genus, String imgUrl) {
        this.genus=genus;
        this.imgUrl=imgUrl;
    }

    public Species(Integer id, String genus, String imgUrl) {
        this.genus=genus;
        this.imgUrl=imgUrl;
        this.Id=id;
    }

}
