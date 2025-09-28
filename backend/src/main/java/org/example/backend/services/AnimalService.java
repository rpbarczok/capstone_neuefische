package org.example.backend.services;

import org.example.backend.dtos.AnimalDto;
import org.example.backend.dtos.AnimalIdInputDto;
import org.example.backend.exceptions.*;
import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.models.Terrarium;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
import org.example.backend.repositories.TerrariumRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    private final SpeciesRepository speciesRepository;

    private final TerrariumRepository terrariumRepository;

    public AnimalService(AnimalRepository animalRepository,
                         SpeciesRepository speciesRepository,
                         TerrariumRepository terrariumRepository) {
        this.animalRepository = animalRepository;
        this.speciesRepository = speciesRepository;
        this.terrariumRepository = terrariumRepository;
    }

    LocalDate parseStringToLocalDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            throw new BadRequestException("Invalid birth date: " + date);
        }
    }

    Species parseGenusToSpecies(String genus) {
        Species species = speciesRepository.getSpeciesByGenus(genus);
        if (species == null) {
            throw new NameNotFoundException("Species", genus);
        } else {
            return species;
        }
    }

    Terrarium parseNameToTerrarium(String name) {
        Terrarium terrarium = terrariumRepository.getTerrariumByName(name);
        if (terrarium == null) {
            throw new NameNotFoundException("Terrarium", name);
        } else {
            return terrarium;
        }
    }

    public List<Animal> getAllAnimals(){
        return (List<Animal>) animalRepository.findAll();
    }

    public Animal getAnimalById(int id) {
        Optional<Animal> foundAnimal = animalRepository.findById(id);
        if (foundAnimal.isPresent()) {
            return foundAnimal.get();
        } else {
            throw new NotFoundException("animal", id);
        }
    }

    public Animal addOneAnimal(AnimalDto animal) {
        System.out.println(animal);
        Animal newAnimal = new Animal(animal.getName(),
                parseStringToLocalDate(animal.getBirthDate()),
                parseGenusToSpecies(animal.getSpecies()),
                parseNameToTerrarium(animal.getTerrarium()),
                Gender.getGenderFromString(animal.getGender()),
                animal.getImgUrl());
        try {
            System.out.println(newAnimal);
            Animal createdAnimal = animalRepository.save(newAnimal);
            System.out.println(createdAnimal);
            return animalRepository.findById(createdAnimal.getId()).orElseThrow(() -> new CreationFailedException("animal", animal.getName()));
        } catch (Exception e) {
            throw new CreationFailedException("animal", animal.getName());
        }

    }

    public Animal updateAnimal (AnimalIdInputDto animalIdInputDto) {
        Optional<Animal> oldAnimal = animalRepository.findById(animalIdInputDto.getId());
        if (oldAnimal.isPresent()) {
            Animal updatedAnimal = new Animal (animalIdInputDto.getId(),
                    animalIdInputDto.getName(),
                    parseStringToLocalDate(animalIdInputDto.getBirthDate()),
                    parseGenusToSpecies(animalIdInputDto.getSpecies()),
                    parseNameToTerrarium(animalIdInputDto.getTerrarium()),
                    Gender.getGenderFromString(animalIdInputDto.getGender()),
                    animalIdInputDto.getImgUrl()
                    );
            Animal savedAnimal = animalRepository.save(updatedAnimal);
            if (savedAnimal == null) {
                throw new UpdateFailedException("animal", animalIdInputDto.getId());
            } else {
                return savedAnimal;
            }
        } else {
            throw new NotFoundException("animal", animalIdInputDto.getId());
        }
    }

    public void deleteAnimal(int id) {
        Optional<Animal> animal = animalRepository.findById(id);
        if (animal.isPresent()) {
            try {
                animalRepository.deleteById(id);
                Optional<Animal> deletedAnimal = animalRepository.findById(id);
                if (deletedAnimal.isPresent()) {
                    throw new DeletionFailedException("", "animal", id);
                }
            } catch (Exception e) {
                throw new DeletionFailedException(e.getMessage(), "animal", id);
            }
        } else {
            throw new NotFoundException("animal", id);
        }
    }
}
