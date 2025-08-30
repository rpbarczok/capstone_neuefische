package org.example.backend.controllers;

import org.example.backend.dtos.AnimalDto;
import org.example.backend.dtos.AnimalIdDto;
import org.example.backend.models.Animal;
import org.example.backend.services.AnimalService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<AnimalIdDto> getAllAnimals(){
        List<Animal> animals = animalService.getAllAnimals();
        ArrayList<AnimalIdDto> animalIdDtos = new ArrayList<>();
        for(Animal animal : animals){
            animalIdDtos.add(transformAnimalToIdDto(animal));
        }
        return animalIdDtos;
    }

    private AnimalIdDto transformAnimalToIdDto(Animal animal){
        return new AnimalIdDto(animal.getId(),
                animal.getName(),
                animal.getBirthDate().toString(),
                animal.getAge(),
                animal.getSpecies().getGenus(),
                animal.getGender().toString());
    }

    @PostMapping
    public Animal addAnimal(@RequestBody AnimalDto animal){
        return animalService.addOneAnimal(animal).orElse(null);
    }
}
