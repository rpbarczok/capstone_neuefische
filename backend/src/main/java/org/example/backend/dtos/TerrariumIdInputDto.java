package org.example.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TerrariumIdInputDto {
        private int  id;
        private String name;
        private int height;
        private int width;
        private int depth;
}
