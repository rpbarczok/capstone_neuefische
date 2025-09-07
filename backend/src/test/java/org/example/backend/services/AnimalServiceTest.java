package org.example.backend.services;

import org.example.backend.dtos.AnimalDto;
import org.example.backend.dtos.AnimalIdInputDto;
import org.example.backend.exceptions.*;
import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
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

    @Test
    void getAnimalById_shouldReturnAnimal_whenAnimalExists() {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species species = new Species(1, "Phidippus regius");
        Animal animal = new Animal(1,
                "Leonie",
                LocalDate.of(2025,5,8),
                species,
                Gender.FEMALE);


        when(animalRepo.findById(1)).thenReturn(Optional.of(animal));

        assertEquals(animal,service.getAnimalById(1));
    }

    @Test
    void getAnimalById_shouldThrowNotFoundError_whenAnimalDoesntExist() {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);

        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        when(animalRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getAnimalById(1));
    }

    @Test
    void updateAnimal_shouldReturnUpdatedAnimal_whenCalledWithValidData () {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species oldSpecies = new Species(1, "Phidippus regius");
        Species newSpecies = new Species(2, "Phidippus ardens");

        Animal oldAnimal = new Animal(
                1,
                "Leonie",
                LocalDate.of(2025, 5,8),
                oldSpecies,
                Gender.FEMALE
                );

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "männlich"
        );

        Animal newAnimal = new Animal(
                1,
                "Leon",
                LocalDate.of(2025,5,8),
                newSpecies,
                Gender.MALE
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(oldAnimal));
        when(speciesRepo.getSpeciesByGenus(newSpecies.getGenus())).thenReturn(newSpecies);
        when(animalRepo.save(newAnimal)).thenReturn(newAnimal);

        // when
        Animal updatedAnimal = service.updateAnimal(newAnimalDto);

        // then
        assertEquals(newAnimal, updatedAnimal);
    }

    @Test
    void updateAnimal_shouldReturnNotFoundException_whenCalledWithValidDataOnNonExistingAnimal () {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "männlich"
        );

        when(animalRepo.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> service.updateAnimal(newAnimalDto));
    }

    @Test
    void updateAnimal_shouldThrowBadRequest_whenCalledWithNotParsableBirthDate () {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species oldSpecies = new Species(1, "Phidippus regius");
        Species newSpecies = new Species(2, "Phidippus ardens");

        Animal oldAnimal = new Animal(
                1,
                "Leonie",
                LocalDate.of(2025, 5,8),
                oldSpecies,
                Gender.FEMALE
        );

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "asdg",
                "Phidippus ardens",
                "männlich"
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(oldAnimal));

        // when & then
        assertThrows(BadRequestException.class, () -> service.updateAnimal(newAnimalDto));
    }

    @Test
    void updateAnimal_shouldThrowNameNotFoundException_whenCalledWithNonExistingSpecies () {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species oldSpecies = new Species(1, "Phidippus regius");
        Species newSpecies = new Species(2, "Phidippus ardens");

        Animal oldAnimal = new Animal(
                1,
                "Leonie",
                LocalDate.of(2025, 5,8),
                oldSpecies,
                Gender.FEMALE
        );

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "männlich"
        );


        when(animalRepo.findById(1)).thenReturn(Optional.of(oldAnimal));
        when(speciesRepo.getSpeciesByGenus(newSpecies.getGenus())).thenReturn(null);

        // then
        // when & then
        assertThrows(NameNotFoundException.class, () -> service.updateAnimal(newAnimalDto));
    }

    @Test
    void updateAnimal_shouldReturnUpdateFailedException_whenUpdateFails () {
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species oldSpecies = new Species(1, "Phidippus regius");
        Species newSpecies = new Species(2, "Phidippus ardens");

        Animal oldAnimal = new Animal(
                1,
                "Leonie",
                LocalDate.of(2025, 5,8),
                oldSpecies,
                Gender.FEMALE
        );

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "männlich"
        );

        Animal newAnimal = new Animal(
                1,
                "Leon",
                LocalDate.of(2025,5,8),
                newSpecies,
                Gender.MALE
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(oldAnimal));
        when(speciesRepo.getSpeciesByGenus(newSpecies.getGenus())).thenReturn(newSpecies);
        when(animalRepo.save(newAnimal)).thenReturn(null);

        // then
        assertThrows(UpdateFailedException.class, () -> service.updateAnimal(newAnimalDto));
    }

     @Test
    void deleteAnimal_shouldReturnVoid_whenCalledWithExistingSpecies() {
        // given
         AnimalRepository animalRepo = mock(AnimalRepository.class);
         SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
         AnimalService service = new AnimalService(animalRepo, speciesRepo);

         Species species = new Species(1, "Phidippus regius");

         Animal animal = new Animal(
                 1,
                 "Leonie",
                 LocalDate.of(2025, 5,8),
                 species,
                 Gender.FEMALE
         );

         when(animalRepo.findById(1)).thenReturn(Optional.of(animal), Optional.empty());

         // When
         service.deleteAnimal(1);

         // Then
         verify(animalRepo, times(1)).deleteById(1);
     }

     @Test
    void deleteAnimal_shouldThrowNotFound_whenCalledWithNonExistingAnimal () {
         // given
         AnimalRepository animalRepo = mock(AnimalRepository.class);
         SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
         AnimalService service = new AnimalService(animalRepo, speciesRepo);

         Species species = new Species(1, "Phidippus regius");

         Animal animal = new Animal(
                 1,
                 "Leonie",
                 LocalDate.of(2025, 5,8),
                 species,
                 Gender.FEMALE
         );

         when(animalRepo.findById(1)).thenReturn(Optional.empty());

         // when & then
         assertThrows(NotFoundException.class, () -> service.deleteAnimal(1));
     }

    @Test
    void deleteAnimal_shouldThrowDeletionFailed_whenAnimalDeletionFails() {
        // given
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species species = new Species(1, "Phidippus regius");

        Animal animal = new Animal(
                1,
                "Leonie",
                LocalDate.of(2025, 5,8),
                species,
                Gender.FEMALE
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(animal));
        doThrow(new RuntimeException("something went wrong"))
                .when(animalRepo)
                .deleteById(1);
        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteAnimal(1));
    }

    @Test
    void deleteAnimal_shouldThrowDeletionFailed_whenDeletedAnimalStillExists() {
        // given
        AnimalRepository animalRepo = mock(AnimalRepository.class);
        SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
        AnimalService service = new AnimalService(animalRepo, speciesRepo);

        Species species = new Species(1, "Phidippus regius");

        Animal animal = new Animal(
                1,
                "Leonie",
                LocalDate.of(2025, 5,8),
                species,
                Gender.FEMALE
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(animal));

        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteAnimal(1));
    }


}
