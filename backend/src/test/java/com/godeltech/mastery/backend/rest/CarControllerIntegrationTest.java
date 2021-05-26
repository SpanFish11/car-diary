package com.godeltech.mastery.backend.rest;

import static java.lang.String.valueOf;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.utils.TemplateDoc;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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
    "/tests/rest/cars/data.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/tests/drop.sql"}, executionPhase = AFTER_TEST_METHOD)
class CarControllerIntegrationTest {

  private static final String ALL_CARS_PAGE_ONE =
      "src/test/resources/tests/rest/cars/allCars.json";
  private static final String CAR_BY_ID = "src/test/resources/tests/rest/cars/findCarById.json";
  private static final String API_CARS = "/api/v1/cars";
  private static final String GET_BY_ID = API_CARS + "/{car_id}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TemplateDoc templateDoc;

//  @ParameterizedTest
//  @MethodSource("provideGetAllCarsPageSizeAndFilter")
//  void getAllCars(final String pathname, final Integer page, final Integer size,
//      final Filter filter) throws Exception {
//    final var params = templateDoc.mapParams(page, size, filter);
//    final var jsonString =
//        objectMapper.writeValueAsString(
//            objectMapper.readValue(
//                new File(pathname), JSONObject.class));
//
//    mockMvc
//        .perform(get(API_CARS).params(params))
//        .andExpect(content().contentType(APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(content().json(jsonString));
//  }

  // TODO page

  @Test
  void getCarById() throws Exception {
    final var carId = 1;
    final var jsonString =
        objectMapper.writeValueAsString(
            objectMapper.readValue(
                new File(CAR_BY_ID), JSONObject.class));

    mockMvc
        .perform(get(GET_BY_ID, carId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonString));
  }

  @Test
  void getCarById_given_carId_should_status_isNotFound() throws Exception {
    final var carId = 1000;
    mockMvc
        .perform(get(GET_BY_ID, carId))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(EntityNotFoundException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Could not find any car with the ID 1000.")));
  }

  @Test
  void createCar() {
  }

  @Test
  void updateCarPhoto() {
  }

  private static Stream<Arguments> provideGetAllCarsPageSizeAndFilter() {
    return Stream
        .of(Arguments
            .of(ALL_CARS_PAGE_ONE, 0, 5, new Filter(1L, "null", "null", 2020, 2121, 2121)));
  }
}