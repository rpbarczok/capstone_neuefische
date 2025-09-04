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
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AnimalServiceTest {

    @Test
    void getAllAnimals_returnsEmptyList_WhenDBIsEmpty() {
        //given
        List<Animal> animalList = new ArrayList<>();

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(animalRepo.findAll()).thenReturn(animalList);

        // when
        List<Animal> result = service.getAllAnimals();

        assertEquals(0,result.size());
    }

    @Test
    void getAllAnimals_returnsAnimalsList_WhenAnimalListIsNotEmpty() {
        // given
        Animal animal = new Animal("Leonie", LocalDate.of(2025, 5, 8),  new Species("Phidippus regius"), Gender.FEMALE);
        ArrayList<Animal> animalList = new ArrayList<>();
        animalList.add(animal);

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(animalRepo.findAll()).thenReturn(animalList);

        //when
        List<Animal> result = service.getAllAnimals();

        //then
        assertEquals(1,result.size());
    }

    @Test
    void addOneAnimal_shouldReturnAnimal_WhenCalledWithValidData() {
        // given
        LocalDate date = LocalDate.of(2025, 5, 8);

        AnimalDto animalInput = new AnimalDto("Leonie", date.toString(),  "Phidippus regius", "weiblich");

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);

        Species speciesInput = new  Species(1, "Phidippus regius");

        Animal animalWithoutId = new Animal("Leonie", date,  speciesInput, Gender.FEMALE);
        Animal animalWithId = new Animal(1, "Leonie", date,  speciesInput, Gender.FEMALE);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(speciesInput);
        when(animalRepo.save(animalWithoutId)).thenReturn(animalWithId);
        when(animalRepo.findById(animalWithId.getId())).thenReturn(Optional.of(animalWithId));

        // when
        Animal actual = service.addOneAnimal(animalInput);

        // then
        assertEquals(animalWithId, actual);
    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCalledWithNonExistingSpecies() {
        // given
        LocalDate date = LocalDate.of(2025, 5, 8);

        AnimalDto animalInput = new AnimalDto("Leonie", date.toString(),  "Phidippus regius", "weiblich");

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);

        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        // when & then
        assertThrows(NameNotFoundException.class, () -> service.addOneAnimal(animalInput));
    }


    @Test
    void addOneAnimal_shouldThrowException_WhenCalledWithNonParsableStringDate() {
        // given

        AnimalDto animalInput = new AnimalDto("Leonie", "asdf",  "Phidippus regius", "weiblich");

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);

        Species speciesInput = new  Species(1, "Phidippus regius");

        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(speciesInput);

        // when & then
        assertThrows(BadRequestException.class, () -> service.addOneAnimal(animalInput));

    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCreationFailed() {
        // given
        LocalDate date = LocalDate.of(2025, 5, 8);

        AnimalDto animalInput = new AnimalDto("Leonie", date.toString(),  "Phidippus regius", "weiblich");

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);

        Species speciesInput = new  Species(1, "Phidippus regius");

        Animal animalWithoutId = new Animal("Leonie", date,  speciesInput, Gender.FEMALE);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(speciesInput);
        when(animalRepo.save(animalWithoutId)).thenReturn(null);

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneAnimal(animalInput));

    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCreatedAnimalWasNotFound() {
        // given
        LocalDate date = LocalDate.of(2025, 5, 8);

        AnimalDto animalInput = new AnimalDto("Leonie", date.toString(),  "Phidippus regius", "weiblich");

        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);

        Species speciesInput = new  Species(1, "Phidippus regius");

        Animal animalWithoutId = new Animal("Leonie", date,  speciesInput, Gender.FEMALE);
        Animal animalWithId = new Animal(1, "Leonie", date,  speciesInput, Gender.FEMALE);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(speciesInput);
        when(animalRepo.save(animalWithoutId)).thenReturn(animalWithId);
        when(animalRepo.findById(animalWithId.getId())).thenReturn(Optional.empty());

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneAnimal(animalInput));

    }
}
