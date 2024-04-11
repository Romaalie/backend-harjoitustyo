package com.romaalie.budjetointiharjoitustyo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
@DirtiesContext
public class MenoeraRestTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void statusOk() throws Exception {

        mockMvc.perform(get("/menoerat"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void responseTypeApplicationJson() throws Exception {

        mockMvc.perform(get("/menoerat"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    //Ongelma jsonBodyn kanssa. Sama ongelma postmanin kanssa.
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void postMenoera_ValidObject_Created() throws Exception {
        String jsonBody = "{\"hinta\": 50.0, \"aikaLeima\": \"2024-04-10\", " +
        "\"maksaja\": {\"id\": 1, \"nimi\": \"Test Maksaja\", \"salasanaHash\": \"TestHash\", \"kayttajaRooli\": \"rooli_user\"}, " +
        "\"paaluokka\": {\"id\": 1, \"nimi\": \"Test Paaluokka\"}, " +
        "\"aliluokka\": {\"id\": 1, \"nimi\": \"Test Aliluokka\", \"paaluokka\": {\"id\": 1, \"nimi\": \"Test Paaluokka\"}}}";
    
    
    
        mockMvc.perform(post("/menoera")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void postMenoera_InvalidObject_BadRequest() throws Exception {
        String jsonBody = "{\"hinta\": 50.0, \"aikaLeima\": \"2024-04-10\", \"lisatietoja\": \"Test\"}";

        mockMvc.perform(post("/menoera")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void deleteMenoeraById_StatusOk() throws Exception {

        mockMvc.perform(delete("/menoera/{id}", 21L))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void deleteMenoeraById_NonExistentId_NotFound() throws Exception {

        mockMvc.perform(delete("/menoera/{id}", 999L))
                .andExpect(status().isNotFound());
    }

}
