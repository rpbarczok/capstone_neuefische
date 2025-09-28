package org.example.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Terrarium {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private int height;

    @Column(nullable = false)
    private int width;

    @Column(nullable = false)
    private int depth;

    @Transient
    private int volume;

    public Terrarium(String name, int height, int width, int depth) {
        this.name = name;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.volume = (height * width * depth) / 1000;
    }

    public Terrarium(int id, String name, int height, int width, int depth) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.volume = (height * width * depth) / 1000;
    }
}
