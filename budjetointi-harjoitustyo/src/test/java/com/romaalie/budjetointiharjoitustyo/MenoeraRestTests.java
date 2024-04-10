package com.romaalie.budjetointiharjoitustyo;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class MenoeraRestTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("/menoerat")).andExpect(status().isOk());
    }

    @Test
    public void responseTypeApplicationJson() throws Exception {
        mockMvc.perform(get("/menoerat")).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    //Ongelma jsonBodyn kanssa. Sama ongelma postmanin kanssa.
    @Test
    public void postMenoera_ValidObject_Created() throws Exception {
        String jsonBody = "{\"hinta\": 50.0, \"aikaLeima\": \"2024-04-10\", \"lisatietoja\": \"Test\", \"maksaja\": {\"id\": 1, \"nimi\": \"Test Maksaja\", \"salasanaHash\": \"TestHash\", \"kayttajaRooli\": \"rooli_user\"}, \"paaluokka\": {\"id\": 1, \"nimi\": \"Test Paaluokka\"}, \"aliluokka\": {\"id\": 1, \"nimi\": \"Test Aliluokka\"}}";

        mockMvc.perform(post("/menoera")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void postMenoera_InvalidObject_BadRequest() throws Exception {
        String jsonBody = "{\"hinta\": 50.0, \"aikaLeima\": \"2024-04-10\", \"lisatietoja\": \"Test\"}";

        mockMvc.perform(post("/menoera")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteMenoeraById_StatusOk() throws Exception {
        mockMvc.perform(delete("/menoera/{id}", 1L)).andExpect(status().isOk());
    }

    @Test
    public void deleteMenoeraById_NonExistentId_NotFound() throws Exception {
        mockMvc.perform(delete("/menoera/{id}", 999L)).andExpect(status().isNotFound());
    }


    // Autentikoinnin hoitaminen testeihin.

    private ResultActions performAuthenticatedGetRequest(String url) throws Exception {
        String authHeader = "Basic " + getBasicAuthHeader("admin", "admin");

        return mockMvc.perform(get(url)
                .header("Authorization", authHeader))
                .andExpect(status().isOk());
    }

    private String getBasicAuthHeader(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
        return new String(encodedAuth);
    }

}
