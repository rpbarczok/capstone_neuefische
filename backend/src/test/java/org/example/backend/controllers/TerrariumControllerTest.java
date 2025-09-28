package org.example.backend.controllers;

import org.example.backend.models.Terrarium;
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

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class TerrariumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TerrariumRepository terrariumRepo;

    Terrarium terrarium = new Terrarium( "Leonies Castle",
            30, 20,20);


    @Test
    void getAllTerraria_shouldReturnListOfTerraria_whenCalled() throws Exception {
        // Given
        terrariumRepo.save(terrarium);
        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/terraria"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                          {
                            "name": "Leonies Castle",
                            "height":30,
                            "width": 20,
                            "depth": 20
                          }
                        ]
                        """
                ));
    }

    @Test
    void addTerrarium_shouldReturnTerrarium_whenCalledWithValidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/terraria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                            "name": "Leonies Castle",
                            "height":30,
                            "width": 20,
                            "depth": 20
                          }
                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                            "name": "Leonies Castle",
                            "height":30,
                            "width": 20,
                            "depth": 20,
                            "volume": 12
                          }
                """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty());
    }

    @Test
    void getTerrariumById_shouldReturnsTerrarium_WhenTerrariumExists() throws Exception {
        //Given
        terrariumRepo.save(terrarium);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/terraria/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                          {
                            "id": 1,
                            "name": "Leonies Castle",
                            "height":30,
                            "width": 20,
                            "depth": 20
                          }
                        """
                ));
    }

    @Test
    void getTerrariaById_shouldThrowNotFound_WhenTerrariaDoesntExists() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/terraria/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteTerrariaById_shouldReturnNotContent_WhenSuccess() throws Exception {
        //Given
        terrariumRepo.save(terrarium);

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/terraria/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteTerrariumById_shouldReturn404_whenNotFound() throws Exception {
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/terraria/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateTerraria_shouldReturnUpdatedTerraria_whenCalledWithValidDateAndOnExistingTerraria() throws Exception {
        // given
        terrariumRepo.save(terrarium);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/terraria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 1,
                              "name": "Benes Castle",
                              "height":30,
                              "width": 20,
                              "depth": 20
                             }
                    """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "id": 1,
                            "name": "Benes Castle",
                            "height":30,
                            "width": 20,
                            "depth": 20,
                            "volume": 12
                        }
                        """));
    }

    @Test
    void updateTerrariumById_shouldReturnBadRequest_whenIdFromInstanceAndFromURIDontMatch() throws Exception {
        // given
        terrariumRepo.save(terrarium);

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/terraria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 2,
                              "name": "Benes Castle",
                              "height":30,
                              "width": 20,
                              "depth": 20
                             }
                    """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void updateTerrariumById_shouldReturnNotFound_whenTerrariumDoesntExist() throws Exception {

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/terraria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "id": 1,
                              "name": "Leonies Castle",
                              "height":30,
                              "width": 20,
                              "depth": 20,
                              "volume": 12
                             }
                    """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}