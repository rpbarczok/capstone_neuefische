package org.example.backend.services;

import org.example.backend.dtos.AnimalDto;
import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    private final SpeciesRepository speciesRepository;

    public AnimalService(AnimalRepository animalRepository,
                         SpeciesRepository speciesRepository) {
        this.animalRepository = animalRepository;
        this.speciesRepository = speciesRepository;
    }

    public List<Animal> getAllAnimals(){
        return (List<Animal>) animalRepository.findAll();
    }

    public Optional<Animal> addOneAnimal(AnimalDto animal) {
        Species species = speciesRepository.getSpeciesByGenus(animal.getSpecies());
        Gender gender = Gender.getGenderFromString(animal.getGender());
        LocalDate birthday = LocalDate.parse(animal.getBirthDate());
        Animal newAnimal = new Animal(animal.getName(), birthday, species, gender);
        Animal createdAnimal = animalRepository.save(newAnimal);
        return animalRepository.findById(createdAnimal.getId());
    }

}
