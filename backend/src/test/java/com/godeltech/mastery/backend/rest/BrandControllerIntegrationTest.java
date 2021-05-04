package com.godeltech.mastery.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/tests/brands/initTestDB.sql")
class BrandControllerIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Test
  void getAllBrands() throws Exception {
    final var jsonString =
        objectMapper.writeValueAsString(
            objectMapper.readValue(
                new File("src/test/resources/tests/brands/allBrands.json"), JSONArray.class));

    mockMvc
        .perform(get("/api/v1/brands"))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonString));
  }

  @Test
  void getAllModelByCorrectId() throws Exception {
    final var jsonString =
        objectMapper.writeValueAsString(
            objectMapper.readValue(
                new File("src/test/resources/tests/brands/modelsById.json"), JSONArray.class));

    mockMvc
        .perform(get("/api/v1/brands/{brand_id}/models", 2))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonString));
  }

  @Test
  void getAllModelByIncorrectId() throws Exception {
    mockMvc
        .perform(get("/api/v1/brands/{brand_id}/models", 7))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(EntityNotFoundException.class)))
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException().getMessage(),
                    is("Could not find any brand with the ID 7.")));
  }
}
