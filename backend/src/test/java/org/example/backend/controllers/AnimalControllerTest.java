package org.example.backend.controllers;

import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
import org.example.backend.services.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode =  DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AnimalControllerTest {

     @Autowired
     private MockMvc mockMvc;

    @Autowired
    private SpeciesRepository speciesRepo;

    @Autowired
     private AnimalRepository animalRepo;


    @Test
    void getAllAnimals_shouldReturnListOfAnimals_whenCalled() throws Exception {
        // Given
        Species species = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        Species newSpecies = speciesRepo.save(species);
        LocalDate birthDate = LocalDate.of(2025, 5,8);
        Animal animal = new Animal("Leonie", birthDate, newSpecies, Gender.FEMALE);
        Animal result = animalRepo.save(animal);
        System.out.println(result);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "id": 1,
                            "name": "Leonie",
                            "species" : "Phidippus regius",
                            "birthDate": "2025-05-08",
                            "gender": "weiblich"
                          }
                        ]
                        """
                ));
    }

    @Test
    void addAnimal_shouldReturnCreatedAnimal_WhenCalledWithValidData() throws Exception {
        // Given
        Species species = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Leonie",
                          "species": "Phidippus regius",
                          "birthDate": "2024-05-08",
                          "gender": "weiblich"
                        }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                            {
                            "name": "Leonie",
                            "species": "Phidippus regius",
                            "gender": "weiblich"
                            }
                        """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void addAnimal_shouldReturn400_WhenDateIsNotParsable() throws Exception {
        // Given
        Species species = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Leonie",
                          "species": "Phidippus regius",
                          "birthDate": "2asdgjlöafgj",
                          "gender": "weiblich"
                        }
                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addAnimal_shouldReturn404_WhenSpeciesIsNotFound() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Leonie",
                          "species": "Phidippus regius",
                          "birthDate": "2024-05-08",
                          "gender": "weiblich"
                        }
                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAnimalById_shouldReturnAnimal_WhenAnimalExists() throws Exception {
        // Given
        Species species = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);
        Animal animal = new Animal(
                "Leonie",
                LocalDate.of(2025,5,8),
                species,
                Gender.FEMALE
                );
        animalRepo.save(animal);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus regius",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich"
                  }
                """));
    }

    @Test
    void getAnimalsById_shouldThrowNotFound_WhenAnimalDoesntExists() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateAnimalById_shouldReturnUpdatedAnimal_whenCalledWithValidDataAndOnExistingAnimal() throws Exception {
        // Given
        Species species1 = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species1);
        Species species2 = new Species("Phidippus ardens", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg");
        speciesRepo.save(species2);
        Animal animal = new Animal(
                "Leonie",
                LocalDate.of(2025,5,8),
                species1,
                Gender.FEMALE
        );
        animalRepo.save(animal);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus ardens",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich"
                  }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                  {
                      "id": 1,
                      "name": "Leonie",
                      "species": "Phidippus ardens",
                      "birthDate": "2025-05-08",
                      "gender": "weiblich"
                  }
                """));
    }

     @Test
     void updateAnimalById_shouldReturnBadRequest_whenIdFromInstanceAndFromURIDontMatch() throws Exception {
         // Given
         Species species1 = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
         speciesRepo.save(species1);
         Species species2 = new Species("Phidippus ardens", "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg");
         speciesRepo.save(species2);
         Animal animal = new Animal("Leonie",
                 LocalDate.of(2025,5,8),
                 species1,
                 Gender.FEMALE
         );
         animalRepo.save(animal);

         // When & Then
         mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content("""
                  {
                  "id": 2,
                  "name": "Leonie",
                  "species": "Phidippus ardens",
                  "birthDate": "2025-05-08",
                  "gender": "FEMALE"
                  }
                """))
                 .andExpect(MockMvcResultMatchers.status().isBadRequest());
     }

    @Test
    void updateAnimalById_shouldReturnNotFound_whenAnimalDoesntExist() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus ardens",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich"
                  }
                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateAnimalById_shouldReturnNotFound_whenSpeciesDoesntExist() throws Exception {
        // Given
        Species species1 = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species1);
        Animal animal = new Animal("Leonie",
                LocalDate.of(2025,5,8),
                species1,
                Gender.FEMALE
        );
        animalRepo.save(animal);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus ardens",
                  "birthDate": "2025-05-08",
                  "gender": "FEMALE"
                  }
                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteAnimal_shouldReturnNoContent_whenAnimalWasDeletedSuccessfully() throws Exception {
        // Given
        Species species = new Species("Phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);
        Animal animal = new Animal(
                "Leonie",
                LocalDate.of(2025,5,8),
                species,
                Gender.FEMALE
        );
        animalRepo.save(animal);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/animals/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteAnimal_shouldReturnNotFound_whenAnimalDoesNotExist() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/animals/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}