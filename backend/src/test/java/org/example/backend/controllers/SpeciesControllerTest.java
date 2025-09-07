package org.example.backend.controllers;

import org.example.backend.models.Species;
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


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SpeciesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpeciesRepository speciesRepo;

    @Test
    void getSpecies_shouldReturnListOfSpecies_whenCalled() throws Exception {
        //Given
        Species species = new Species("phidippus regius");
        speciesRepo.save(species);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/species"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "genus": "phidippus regius"
                          }
                        ]
                        """
                ));
    }

    @Test
    void addSpecies_shouldReturnSpecies_whenCalledWithValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/species")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "genus": "phidippus regius"
                    }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                      "genus": "phidippus regius"
                    }
                """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getSpeciesById_shouldReturnsSpecies_WhenSpeciesExists() throws Exception {
        //Given
        Species species = new Species("phidippus regius");
        speciesRepo.save(species);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/species/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                          {
                            "id": 1,
                            "genus": "phidippus regius"
                          }
                        """
                ));
    }

    @Test
    void getSpeciesById_shouldThrowNotFound_WhenSpeciesDoesntExists() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/species/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}