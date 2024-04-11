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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    // /get testi
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void statusOk() throws Exception {

        mockMvc.perform(get("/menoerat"))
                .andExpect(status().isOk());
    }

    // Palautusmuotona JSON testi.
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void responseTypeApplicationJson() throws Exception {

        mockMvc.perform(get("/menoerat"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    // Uuden menoeran luonti testi.
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void postMenoera_ValidObject_Created() throws Exception {

        String jsonBody = "{"
                + "\"hinta\": 123456.0, "
                + "\"aikaLeima\": \"2021-01-01\", "
                + "\"lisatietoja\": null, "
                + "\"maksaja\": {"
                + "\"id\": 2, "
                + "\"nimi\": \"Tero\", "
                + "\"salasanaHash\": \"$2y$10$GDTmEuglS.PUfitb5Q5IzeaQOf.7zcqVRQpqzmvW21J9DQnbwZlQ2\", "
                + "\"kayttajaRooli\": \"ROLE_kayttaja\""
                + "}, "
                + "\"paaluokka\": {"
                + "\"id\": 3, "
                + "\"nimi\": \"Pakolliset\""
                + "}, "
                + "\"aliluokka\": {"
                + "\"id\": 9, "
                + "\"nimi\": \"Vesi\", "
                + "\"paaluokka\": 3"
                + "}"
                + "}";

        mockMvc.perform(post("/menoera")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    // Luontitesti, tarkoitus epäonnistua.
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void postMenoera_InvalidObject_BadRequest() throws Exception {
        String jsonBody = "{\"hinta\": 50.0, \"aikaLeima\": \"2024-04-10\", "
                + "\"maksaja\": {\"id\": 1}, "
                + "\"paaluokka\": {\"id\": 1}, "
                + "\"aliluokka\": {\"id\": 1}}";

        mockMvc.perform(post("/menoera")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // Poistotesti, tarkoitus onnistua.
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void deleteMenoeraById_StatusOk() throws Exception {

        mockMvc.perform(delete("/menoera/{id}", 21L))
                .andExpect(status().isOk());
    }

    // Poistotesti, tarkoitus epäonnistua.
    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    public void deleteMenoeraById_NonExistentId_NotFound() throws Exception {

        mockMvc.perform(delete("/menoera/{id}", 999L))
                .andExpect(status().isNotFound());
    }

}
