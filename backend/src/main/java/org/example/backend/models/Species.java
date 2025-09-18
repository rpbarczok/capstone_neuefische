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

    private String origin;
    public Species(String genus, String imgUrl, String origin) {
        this.genus=genus;
        this.imgUrl=imgUrl;
        this.origin=origin;
    }

    public Species(Integer id, String genus, String imgUrl, String origin) {
        this.genus=genus;
        this.imgUrl=imgUrl;
        this.origin=origin;
        this.Id=id;
    }

}
