package com.godeltech.mastery.backend.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/tests/equipments/initDb.sql")
class EquipmentControllerIntegrationTest {

  private static final String ALL_EQUIPMENTS =
      "src/test/resources/tests/equipments/allEquipments.json";
  private static final String API_EQUIPMENTS = "/api/v1/equipments";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getAllEquipments() throws Exception {
    final var equipments =
        objectMapper
            .writeValueAsString(objectMapper.readValue(new File(ALL_EQUIPMENTS), JSONArray.class));

    mockMvc
        .perform(get(API_EQUIPMENTS))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(equipments));
  }
}