package org.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrariumIdOutputDto {
    private int id;
    private String name;
    private int depth;
    private int width;
    private int height;
    private int volume;
}
