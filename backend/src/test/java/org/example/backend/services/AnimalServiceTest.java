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

    AnimalRepository animalRepo = mock(AnimalRepository.class);
    SpeciesRepository speciesRepo = mock(SpeciesRepository.class);
    TerrariumRepository terrariumRepo = mock(TerrariumRepository.class);
    AnimalService service = new AnimalService(animalRepo, speciesRepo, terrariumRepo);

    LocalDate birthDate = LocalDate.of(2025, 5, 8);

    Species species1Id = new  Species(1,
            "Phidippus regius",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg",
            "Karibik, Florida");
    Species species2Id = new Species(2,
            "Phidippus ardens",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg",
            "Mexiko");
    Terrarium terrarium = new Terrarium("Leonies Castle",
            30,
            20,
            20);
    Terrarium terrariumId = new Terrarium(1,
            "Leonies Castle",
            30,
            20,
            20);
    Animal leonie = new Animal(
            "Leonie",
            birthDate,
            species1Id,
            terrariumId,
            Gender.FEMALE,
            "");
    Animal leonieId = new Animal(
            1,
            "Leonie",
            birthDate,
            species1Id,
            terrariumId,
            Gender.FEMALE,
            "");
    AnimalDto leonieInput = new AnimalDto(
            "Leonie",
            birthDate.toString(),
            "Phidippus regius",
            "Leonies Castle",
            "weiblich",
            "");


    @Test
    void getAllAnimals_returnsEmptyList_WhenDBIsEmpty() {
        //given
        List<Animal> animalList = new ArrayList<>();

        when(animalRepo.findAll()).thenReturn(animalList);

        // when
        List<Animal> result = service.getAllAnimals();

        assertEquals(0,result.size());
    }

    @Test
    void getAllAnimals_returnsAnimalsList_WhenAnimalListIsNotEmpty() {
        // given
        ArrayList<Animal> animalList = new ArrayList<>();
        animalList.add(leonie);

        when(animalRepo.findAll()).thenReturn(animalList);

        //when
        List<Animal> result = service.getAllAnimals();

        //then
        assertEquals(1,result.size());
    }

    @Test
    void addOneAnimal_shouldReturnAnimal_WhenCalledWithValidData() {
        // given
        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(species1Id);
        when(terrariumRepo.getTerrariumByName("Leonies Castle")).thenReturn(terrariumId);
        when(animalRepo.save(any())).thenReturn(leonieId);
        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));

        // when
        Animal actual = service.addOneAnimal(leonieInput);

        // then
        assertEquals(leonieId, actual);
    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCalledWithNonExistingSpecies() {
        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(null);
        when(terrariumRepo.getTerrariumByName("Leonies Castle")).thenReturn(terrariumId);

        // when & then
        assertThrows(NameNotFoundException.class, () -> service.addOneAnimal(leonieInput));
    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCalledWithNonExistingTerrarium() {
        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(species1Id);
        when(terrariumRepo.getTerrariumByName("Leonies Castle")).thenReturn(null);

        // when & then
        assertThrows(NameNotFoundException.class, () -> service.addOneAnimal(leonieInput));
    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCalledWithNonParsableStringDate() {
        // given

        AnimalDto animalInput = new AnimalDto("Leonie", "asdf",  "Phidippus regius", "Leonies Castle", "weiblich", "");

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(species1Id);

        // when & then
        assertThrows(BadRequestException.class, () -> service.addOneAnimal(animalInput));
    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCreationFailed() {
        // given

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(species1Id);
        when(terrariumRepo.getTerrariumByName(terrarium.getName())).thenReturn(terrariumId);
        when(animalRepo.save(leonie)).thenReturn(null);

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneAnimal(leonieInput));

    }

    @Test
    void addOneAnimal_shouldThrowException_WhenCreatedAnimalWasNotFound() {
        // given

        when(speciesRepo.getSpeciesByGenus("Phidippus regius")).thenReturn(species1Id);
        when(terrariumRepo.getTerrariumByName(terrarium.getName())).thenReturn(terrariumId);
        when(animalRepo.save(leonie)).thenReturn(leonieId);
        when(animalRepo.findById(leonieId.getId())).thenReturn(Optional.empty());

        // when & then
        assertThrows(CreationFailedException.class, () -> service.addOneAnimal(leonieInput));

    }

    @Test
    void getAnimalById_shouldReturnAnimal_whenAnimalExists() {

        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));

        assertEquals(leonieId,service.getAnimalById(1));
    }

    @Test
    void getAnimalById_shouldThrowNotFoundError_whenAnimalDoesntExist() {

        when(animalRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getAnimalById(1));
    }

    @Test
    void updateAnimal_shouldReturnUpdatedAnimal_whenCalledWithValidData () {

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "Leonies Castle",
                "männlich",
                ""
        );

        Animal newAnimal = new Animal(
                1,
                "Leon",
                LocalDate.of(2025,5,8),
                species2Id,
                terrariumId,
                Gender.MALE,
                ""
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));
        when(speciesRepo.getSpeciesByGenus(species2Id.getGenus())).thenReturn(species2Id);
        when(terrariumRepo.getTerrariumByName(terrarium.getName())).thenReturn(terrariumId);
        when(animalRepo.save(newAnimal)).thenReturn(newAnimal);

        // when
        Animal updatedAnimal = service.updateAnimal(newAnimalDto);

        // then
        assertEquals(newAnimal, updatedAnimal);
    }

    @Test
    void updateAnimal_shouldReturnNotFoundException_whenCalledWithValidDataOnNonExistingAnimal () {

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "Leonies Castle",
                "männlich",
                ""
        );

        when(animalRepo.findById(1)).thenReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> service.updateAnimal(newAnimalDto));
    }

    @Test
    void updateAnimal_shouldThrowBadRequest_whenCalledWithNotParsableBirthDate () {

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "nichtFormatierbar",
                "Phidippus ardens",
                "Leonies Castle",
                "männlich",
                ""
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));

        // when & then
        assertThrows(BadRequestException.class, () -> service.updateAnimal(newAnimalDto));
    }

    @Test
    void updateAnimal_shouldThrowNameNotFoundException_whenCalledWithNonExistingSpecies () {

        AnimalService service = new AnimalService(animalRepo, speciesRepo, terrariumRepo);

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "Leonies Castle",
                "männlich",
                ""
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(leonie));
        when(speciesRepo.getSpeciesByGenus(species2Id.getGenus())).thenReturn(null);

        // then
        // when & then
        assertThrows(NameNotFoundException.class, () -> service.updateAnimal(newAnimalDto));
    }

    @Test
    void updateAnimal_shouldReturnUpdateFailedException_whenUpdateFails () {

        AnimalIdInputDto newAnimalDto = new AnimalIdInputDto(
                1,
                "Leon",
                "2025-05-08",
                "Phidippus ardens",
                "Leonies Castle",
                "männlich",
                ""
        );

        Animal newAnimal = new Animal(
                1,
                "Leon",
                birthDate,
                species2Id,
                terrariumId,
                Gender.MALE,
                ""
        );

        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));
        when(speciesRepo.getSpeciesByGenus(species2Id.getGenus())).thenReturn(species2Id);
        when(terrariumRepo.getTerrariumByName(terrarium.getName())).thenReturn(terrariumId);
        when(animalRepo.save(newAnimal)).thenReturn(null);

        // then
        assertThrows(UpdateFailedException.class, () -> service.updateAnimal(newAnimalDto));
    }

     @Test
    void deleteAnimal_shouldReturnVoid_whenCalledWithExistingSpecies() {
        // given

         when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId), Optional.empty());

         // When
         service.deleteAnimal(1);

         // Then
         verify(animalRepo, times(1)).deleteById(1);
     }

     @Test
    void deleteAnimal_shouldThrowNotFound_whenCalledWithNonExistingAnimal () {
         // given

         when(animalRepo.findById(1)).thenReturn(Optional.empty());

         // when & then
         assertThrows(NotFoundException.class, () -> service.deleteAnimal(1));
     }

    @Test
    void deleteAnimal_shouldThrowDeletionFailed_whenAnimalDeletionFails() {
        // given

        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));
        doThrow(new RuntimeException("something went wrong"))
                .when(animalRepo)
                .deleteById(1);
        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteAnimal(1));
    }

    @Test
    void deleteAnimal_shouldThrowDeletionFailed_whenDeletedAnimalStillExists() {
        // given
        when(animalRepo.findById(1)).thenReturn(Optional.of(leonieId));

        // when & then
        assertThrows(DeletionFailedException.class, () -> service.deleteAnimal(1));
    }


}
