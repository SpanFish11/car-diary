package com.godeltech.mastery.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.CarService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/tests/cars/initTestCars.sql")
class CarControllerIntegrationTest {

  private MockMvc mockMvc;
  @Autowired private CarService carService;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(new CarController(carService)).build();
  }

  @Test
  void getAllCars() throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    final String jsonString =
        mapper.writeValueAsString(
            mapper.readValue(
                new File("src/main/resources/tests/cars/allCars.json"), JSONArray.class));

    mockMvc
        .perform(get("/api/v1/cars"))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonString));
  }

  @Test
  void getCarById() throws Exception {
    final ObjectMapper mapper = new ObjectMapper();
    final String jsonString =
        mapper.writeValueAsString(
            mapper.readValue(
                new File("src/main/resources/tests/cars/findCarById.json"), JSONObject.class));

    mockMvc
        .perform(get("/api/v1/cars/{car_id}", 2))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonString));
  }

  @Test
  void getCarByIncorrectId() {
    assertThrows(
        NestedServletException.class,
        () ->
            mockMvc
                .perform(get("/api/v1/cars/{car_id}", 7))
                .andExpect(status().isNotFound())
                .andExpect(
                    result ->
                        assertThat(
                            result.getResolvedException(),
                            instanceOf(EntityNotFoundException.class)))
                .andExpect(
                    result ->
                        assertThat(
                            result.getResolvedException().getMessage(),
                            is("Could not find any Brand with the ID 7."))));
  }

  @Test
  void createCar() throws Exception {}

  // brandId, modelId, year, vin, mileage
  @Test
  void createCarIncorrectData() {}
  // 3 types of jpeg, jpg, png
  @Test
  void updateCarPhoto() {}

  @Test
  void updateCarPhotoIncorrectMediaType() {}
}
