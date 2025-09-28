package org.example.backend.controllers;

import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.models.Terrarium;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
import org.example.backend.repositories.TerrariumRepository;
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

    @Autowired
    private TerrariumRepository terrariumRepo;

    private final Species species1 = new Species("Phidippus regius",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg",
            "Karibik, Florida");

    private final Species species2 = new Species("Phidippus ardens",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg",
            "Mexiko");

    private final Terrarium terrarium = new Terrarium( "Leonies Castle",
            30, 20,20);

    private final Species species1Id = new Species(1, "Phidippus regius",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg",
            "Karibik, Florida");


    private final Terrarium terrariumId = new Terrarium(1, "Leonies Castle",
            30, 20,20);

    private final LocalDate birthDate = LocalDate.of(2025, 5,8);

    private final Animal leonie = new Animal(
            "Leonie",
            birthDate,
            species1Id,
            terrariumId,
            Gender.FEMALE,
            ""
    );

    @Test
    void getAllAnimals_shouldReturnListOfAnimals_whenCalled() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        animalRepo.save(leonie);
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "id": 1,
                            "name": "Leonie",
                            "species" : "Phidippus regius",
                            "terrarium": "Leonies Castle",
                            "birthDate": "2025-05-08",
                            "gender": "weiblich",
                            "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                          }
                        ]
                        """
                ));
    }

    @Test
    void addAnimal_shouldReturnCreatedAnimal_WhenCalledWithValidData() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "name": "Leonie",
                          "species": "Phidippus regius",
                          "terrarium": "Leonies Castle",
                          "birthDate": "2025-05-08",
                          "gender": "weiblich",
                          "imgUrl": ""
                        }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                            {
                            "name": "Leonie",
                            "species": "Phidippus regius",
                            "terrarium": "Leonies Castle",
                            "birthDate": "2025-05-08",
                            "gender": "weiblich",
                            "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                            }
                        """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void addAnimal_shouldReturn400_WhenDateIsNotParsable() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "Leonie",
                          "species": "Phidippus regius",
                          "terrarium": "Leonies Castle",
                          "birthDate": "falschesFormat",
                          "gender": "weiblich",
                          "imgUrl": ""
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
                          "terrarium": "Leonies Castle",
                          "gender": "weiblich",
                          "imgUrl": ""
                        }
                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getAnimalById_shouldReturnAnimal_WhenAnimalExists() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        animalRepo.save(leonie);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus regius",
                  "terrarium": "Leonies Castle",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich",
                  "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                  }
                """));
    }

    @Test
    void getAnimalById_shouldReturnAnimalWithOwnUrl_WhenAnimalExistsWithOwnUrl() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        Animal leonieWithUrl = new Animal(
                "Leonie",
                birthDate,
                species1Id,
                terrariumId,
                Gender.FEMALE,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg"
        );
        animalRepo.save(leonieWithUrl);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/animals/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus regius",
                  "terrarium": "Leonies Castle",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich",
                  "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg"
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
        speciesRepo.save(species1);
        speciesRepo.save(species2);
        terrariumRepo.save(terrarium);
        animalRepo.save(leonie);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus ardens",
                  "terrarium": "Leonies Castle",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich",
                  "imgUrl": ""
                  }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                  {
                      "id": 1,
                      "name": "Leonie",
                      "species": "Phidippus ardens",
                      "terrarium": "Leonies Castle",
                      "birthDate": "2025-05-08",
                      "gender": "weiblich",
                      "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg"
                  }
                """));
    }

     @Test
     void updateAnimalById_shouldReturnBadRequest_whenIdFromInstanceAndFromURIDontMatch() throws Exception {
         // Given
         speciesRepo.save(species1);
         terrariumRepo.save(terrarium);
         animalRepo.save(leonie);

         // When & Then
         mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content("""
                  {
                  "id": 2,
                  "name": "Leonie",
                  "species": "Phidippus regius",
                  "terrarium": "Leonies Castle",
                  "birthDate": "2025-05-08",
                  "gender": "weiblich",
                  "imgUrl": ""
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
                  "gender": "weiblich",
                  "imgUrl": ""
                  }
                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateAnimalById_shouldReturnNotFound_whenSpeciesDoesntExist() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        animalRepo.save(leonie);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/animals/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                  {
                  "id": 1,
                  "name": "Leonie",
                  "species": "Phidippus ardens",
                  "terrarium": "Leonies Castle",
                  "birthDate": "2025-05-08",
                  "gender": "FEMALE",
                  "imgUrl": ""
                  }
                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteAnimal_shouldReturnNoContent_whenAnimalWasDeletedSuccessfully() throws Exception {
        // Given
        speciesRepo.save(species1);
        terrariumRepo.save(terrarium);
        animalRepo.save(leonie);

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