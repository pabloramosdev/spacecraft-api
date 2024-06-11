package com.movies.spacecraft.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpacecraftControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Get spacecraft for id 7")
    void getSpacecraftById() throws Exception {
        mvc.perform(get("/spacecrafts/7")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 7,
                            "name": "Sulaco",
                            "movie": "Alien"
                        }
                        """));
    }

    @Test
    @DisplayName("Get filter spacecraft for name that contains Mach")
    void getContainsNameSpacecraft() throws Exception {
        mvc.perform(get("/spacecrafts/name?filter=Mach")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("""
                                [
                                  {
                                    "id":10,
                                    "name":"Machine",
                                    "movie":"Contact",
                                    "pilot":"Ellie Arroway"
                                   }
                                ]"""));
    }

    @Test
    @DisplayName("Get paginated spacecraft for page 7 size 1")
    void getAllSpacecraft() throws Exception {
        mvc.perform(get("/spacecrafts?page=7&size=1")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("""
                                {
                                    "page": 7,
                                    "size": 1,
                                    "totalPages": 11,
                                    "totalElements": 11,
                                    "content": [
                                        {
                                            "id": 8,
                                            "name": "Spinner",
                                            "movie": "Blade Runner",
                                            "pilot": "Rick Decarad"
                                        }
                                    ]
                                }"""));
    }

    @Test
    @DisplayName("Get paginated spacecraft for page 4 size 5 then return no content")
    void getPageWithNoConten() throws Exception {
        mvc.perform(get("/spacecrafts?page=4&size=5")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Get spacecraft for non existing id")
    void getEmptySpacecraft() throws Exception {
        mvc.perform(get("/spacecrafts/15")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Post new spacecraft")
    void postSpacecraft() throws Exception {
        mvc.perform(post("/spacecrafts")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "testSpacecraft",\
                                  "movie": "testMovie",\
                                  "pilot": "testPilot"
                                }"""))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Put existing spacecraft")
    void putSpacecraft() throws Exception {
        mvc.perform(put("/spacecrafts/1")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "updatedSpacecraft",\
                                  "movie": "updatedMovie",\
                                  "pilot": "updatedPilot"
                                }"""))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Put partial spacecraft then return bad request")
    void putPartialSpacecraft() throws Exception {
        mvc.perform(put("/spacecrafts/1")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "updatedSpacecraft",\
                                  "movie": "updatedMovie"
                                }"""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Put existing spacecraft")
    void patchSpacecraft() throws Exception {
        mvc.perform(patch("/spacecrafts/8")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {\
                                  "name": "updatedSpacecraft",\
                                  "movie": "updatedMovie"
                                }"""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                {
                                    "id": 8,
                                    "name": "updatedSpacecraft",
                                    "movie": "updatedMovie",
                                    "pilot": "Rick Decarad"
                                }
                                """));
    }

    @Test
    @DisplayName("Delete existing spacecraft")
    void deleteSpacecraft() throws Exception {
        mvc.perform(delete("/spacecrafts/2")
                        .with(httpBasic("admin","admin"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

}