package com.godeltech.mastery.backend.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.godeltech.mastery.backend.util.TestUtils;
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
@Sql(
    scripts = {"/tests/schema.sql", "/tests/rest/equipments/data.sql"},
    executionPhase = BEFORE_TEST_METHOD)
@Sql(
    scripts = {"/tests/drop.sql"},
    executionPhase = AFTER_TEST_METHOD)
class EquipmentControllerIntegrationTest {

  private static final String ALL_EQUIPMENTS =
      "src/test/resources/tests/rest/equipments/allEquipments.json";
  private static final String API_EQUIPMENTS = "/api/v1/equipments";

  @Autowired private MockMvc mockMvc;
  @Autowired private TestUtils testUtils;

  @Test
  void getAllEquipments() throws Exception {
    final var equipments = testUtils.toJSONArray(ALL_EQUIPMENTS);

    mockMvc
        .perform(get(API_EQUIPMENTS))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(equipments));
  }
}
