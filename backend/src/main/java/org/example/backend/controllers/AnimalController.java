package org.example.backend.controllers;

import org.example.backend.dtos.AnimalDto;
import org.example.backend.dtos.AnimalIdInputDto;
import org.example.backend.dtos.AnimalIdOutputDto;
import org.example.backend.exceptions.BadRequestException;
import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.services.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/animals")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    private AnimalIdOutputDto transformAnimalToIdDto(Animal animal){
        String animalImage = animal.getImgUrl();
        if (Objects.equals(animalImage, "")) {animalImage = animal.getSpecies().getImgUrl();}
        return new AnimalIdOutputDto(animal.getId(),
                animal.getName(),
                animal.getBirthDate().toString(),
                ChronoUnit.DAYS.between(animal.getBirthDate(), LocalDate.now()),
                animal.getSpecies().getGenus(),
                Gender.getGenderStringFromGender(String.valueOf(animal.getGender())),
                animalImage
        );
    }

    @GetMapping
    public List<AnimalIdOutputDto> getAllAnimals(){
        List<Animal> animals = animalService.getAllAnimals();
        ArrayList<AnimalIdOutputDto> animalIdOutputDtos = new ArrayList<>();
        for(Animal animal : animals){
            animalIdOutputDtos.add(transformAnimalToIdDto(animal));
        }
        return animalIdOutputDtos;
    }

    @PostMapping
    public AnimalIdOutputDto addAnimal(@RequestBody AnimalDto animalDto){
        Animal animal = animalService.addOneAnimal(animalDto);
        return transformAnimalToIdDto(animal);
    }

    @GetMapping("/{id}")
    public AnimalIdOutputDto getAnimalById(@PathVariable int id){
        Animal animal = animalService.getAnimalById(id);
        return transformAnimalToIdDto(animal);
    }

    @PutMapping("/{id}")
    public AnimalIdOutputDto updateAnimal(@PathVariable int id, @RequestBody AnimalIdInputDto animalIdInputDto){
        if (id == animalIdInputDto.getId()){
            Animal animal = animalService.updateAnimal(animalIdInputDto);
            return transformAnimalToIdDto(animal);
        } else {
            throw new BadRequestException("Id of animal does not match with id of URI.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimal(@PathVariable int id){
        animalService.deleteAnimal(id);
    }
}
