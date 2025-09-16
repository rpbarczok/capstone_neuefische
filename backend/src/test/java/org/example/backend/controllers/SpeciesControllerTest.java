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
        Species species = new Species("phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/species"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "genus": "phidippus regius",
                            "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
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
                      "genus": "phidippus regius",
                      "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                    }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                      "genus": "phidippus regius",
                      "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                    }
                """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getSpeciesById_shouldReturnsSpecies_WhenSpeciesExists() throws Exception {
        //Given
        Species species = new Species("phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/species/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                          {
                            "id": 1,
                            "genus": "phidippus regius",
                            "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
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

    @Test
    void deleteSpeciesById_shouldReturnNotContent_WhenSuccess() throws Exception {
        //Given
        Species species = new Species("phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/species/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteSpeciesById_shouldReturn404_whenNotFound() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/species/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateSpeciesById_shouldReturnUpdatedSpecies_whenCalledWithValidDateAndOnExistingSpecies() throws Exception {
        // given
        Species species = new Species("phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/species/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 1,
                              "genus": "phidippus ardens",
                              "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                             }
                    """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "id": 1,
                            "genus": "phidippus ardens",
                            "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg"
                        }
                        """));
    }

    @Test
    void updateSpeciesById_shouldReturnBadRequest_whenIdFromInstanceAndFromURIDontMatch() throws Exception {
        // given
        Species species = new Species("phidippus regius", "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Phidippus_regius_female_01.jpg/330px-Phidippus_regius_female_01.jpg");
        speciesRepo.save(species);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/species/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 2,
                              "genus": "phidippus ardens",
                              "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg"
                             }
                    """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateSpeciesById_shouldReturnNotFound_whenSpeciesDoesntExist() throws Exception {

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/species/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 1,
                              "genus": "phidippus ardens",
                              "imgUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/0/06/Phidippus_ardens_19872715_cropped.jpg/330px-Phidippus_ardens_19872715_cropped.jpg"
                             }
                    """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}