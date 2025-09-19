package org.example.backend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class TeapotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void iAmATeapot_shouldReturnNotFoundException_whenCalledWithTea() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brew/tea"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("O dear, I haven't found any tea, unfortunately I ran out. Sorry."));
    }

    @Test
    void iAmATeapot_shouldReturnImATeapotException_whenCalledWithCoffee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/brew/coffee"))
                .andExpect(MockMvcResultMatchers.status().isIAmATeapot())
                .andExpect(MockMvcResultMatchers.content().string("I am a teapot,I can't make coffee!"));
    }

}