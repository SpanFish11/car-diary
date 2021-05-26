package com.godeltech.mastery.backend.rest;

import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.godeltech.mastery.backend.exception.EntityNotFoundException;
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
@Sql(scripts = {"/tests/schema.sql",
    "/tests/rest/brands/data.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/tests/drop.sql"}, executionPhase = AFTER_TEST_METHOD)
class BrandControllerIntegrationTest {

  private static final String ALL_BRANDS = "src/test/resources/tests/rest/brands/allBrands.json";
  private static final String MODELS_BY_BRAND_ID =
      "src/test/resources/tests/rest/brands/modelsById.json";
  private static final String API_BRANDS = "/api/v1/brands";
  private static final String API_MODELS_BY_BRAND_ID = "/api/v1/brands/{brand_id}/models";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestUtils testUtils;

  @Test
  void getAllBrands() throws Exception {
    final var brands = testUtils.toJSONArray(ALL_BRANDS);

    mockMvc
        .perform(get(API_BRANDS))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(brands));
  }

  @Test
  void getAllModels_given_brandId_should_status_isOk() throws Exception {
    final var brandId = 2;
    final var models = testUtils.toJSONArray(MODELS_BY_BRAND_ID);

    mockMvc
        .perform(get(API_MODELS_BY_BRAND_ID, brandId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(models));
  }

  @Test
  void getAllModels_given_brandId_should_status_isNotFound() throws Exception {
    final var brandId = 7;
    mockMvc
        .perform(get(API_MODELS_BY_BRAND_ID, brandId))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(EntityNotFoundException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Could not find any brand with the ID 7.")));
  }
}