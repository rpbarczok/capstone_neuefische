package org.example.backend.services;

import org.example.backend.dtos.AnimalDto;
import org.example.backend.exceptions.BadRequestException;
import org.example.backend.exceptions.CreationFailedException;
import org.example.backend.exceptions.NameNotFoundException;
import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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

    public Animal addOneAnimal(AnimalDto animal) {

        Species species = speciesRepository.getSpeciesByGenus(animal.getSpecies());
        if (species == null) {
            throw new NameNotFoundException("Species", animal.getSpecies());
        }

        // function getGenderFromString returns in any case valid gender, so no exception is necessary
        Gender gender = Gender.getGenderFromString(animal.getGender());

        LocalDate birthday;
        try {
            birthday = LocalDate.parse(animal.getBirthDate());
        } catch (Exception e) {
            throw new BadRequestException("Invalid birth date: " + animal.getBirthDate());
        }

        Animal newAnimal = new Animal(animal.getName(), birthday, species, gender);
        try {
            Animal createdAnimal = animalRepository.save(newAnimal);
            return animalRepository.findById(createdAnimal.getId()).orElseThrow(() -> new CreationFailedException("animal", animal.getName()));
        } catch (Exception e) {
            throw new CreationFailedException("animal", animal.getName());
        }

    }

}
