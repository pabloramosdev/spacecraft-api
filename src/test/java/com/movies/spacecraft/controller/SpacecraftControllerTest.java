package com.movies.spacecraft.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("aaa"))
                .andExpect(jsonPath("$.movie").value("movie1"))
                .andExpect(jsonPath("$.pilot").value("pilo1"));
    }

    //@Test
    // TODO: Rewrite test when exception manager were done
    @DisplayName("Get spacecraft for non existing id")
    void getEmptySpacecraft() throws Exception {
        mvc.perform(get("/spacecrafts/15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Post new spacecraft")
    void postSpacecraft() throws Exception {
        mvc.perform(post("/spacecrafts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "testSpacecraft",\
                                  "movie": "testMovie",\
                                  "pilot": "testPilot"
                                }"""))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testSpacecraft"))
                .andExpect(jsonPath("$.movie").value("testMovie"))
                .andExpect(jsonPath("$.pilot").value("testPilot"));
    }

    @Test
    @DisplayName("Put existing spacecraft")
    void putSpacecraft() throws Exception {
        mvc.perform(put("/spacecrafts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "updatedSpacecraft",\
                                  "movie": "updatedMovie",\
                                  "pilot": "updatedPilot"
                                }"""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("updatedSpacecraft"))
                .andExpect(jsonPath("$.movie").value("updatedMovie"))
                .andExpect(jsonPath("$.pilot").value("updatedPilot"));
    }

    @Test
    @DisplayName("Put existing spacecraft")
    void patchSpacecraft() throws Exception {
        mvc.perform(patch("/spacecrafts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "updatedSpacecraft",\
                                  "movie": "updatedMovie"
                                }"""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("updatedSpacecraft"))
                .andExpect(jsonPath("$.movie").value("updatedMovie"))
                .andExpect(jsonPath("$.pilot").value("pilo1"));
    }

    @Test
    @DisplayName("Delete existing spacecraft")
    void deleteSpacecraft() throws Exception {
        mvc.perform(delete("/spacecrafts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}