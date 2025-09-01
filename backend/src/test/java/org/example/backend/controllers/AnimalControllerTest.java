package org.example.backend.controllers;

import org.example.backend.models.Animal;
import org.example.backend.models.Gender;
import org.example.backend.models.Species;
import org.example.backend.repositories.AnimalRepository;
import org.example.backend.repositories.SpeciesRepository;
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
        Species species = new Species("Phidippus regius");
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
                            "gender": "FEMALE"
                          }
                        ]
                        """
                ));
    }


    @Test
    void addAnimal_shouldReturnCreatedAnimal_WhenCalledWithValidData() throws Exception {
        // Given
        Species species = new Species("Phidippus regius");
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
                            "species":
                              {
                                "genus": "Phidippus regius"
                              } ,
                            "gender": "FEMALE"
                            }
                        """
                ))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }
}