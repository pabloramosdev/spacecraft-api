package com.movies.spacecraft.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpacecraftControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Get spacecraft for id 1")
    void getSpacecraftById() throws Exception {
        mvc.perform(get("/spacecrafts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("aaa"))
                .andExpect(jsonPath("$.movie").value("movie1"))
                .andExpect(jsonPath("$.pilot").value("pilo1"));
    }

    @Test
    @DisplayName("Get spacecraft for non existing id")
    void getEmptySpacecraft() throws Exception {
        mvc.perform(get("/spacecrafts/15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}