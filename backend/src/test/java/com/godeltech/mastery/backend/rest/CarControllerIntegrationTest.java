package com.godeltech.mastery.backend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.domain.dto.CarCreateRequest;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.CarService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.NestedServletException;

import java.io.File;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/tests/cars/initTestCars.sql")
class CarControllerIntegrationTest {

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;
  @Autowired private CarService carService;

  @BeforeEach
  void setUp() {
    mockMvc = standaloneSetup(new CarController(carService)).build();
  }

  @Test
  void getAllCars() throws Exception {
    final var jsonString =
        objectMapper.writeValueAsString(
            objectMapper.readValue(
                new File("src/main/resources/tests/cars/allCars.json"), JSONArray.class));

    mockMvc
        .perform(get("/api/v1/cars"))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonString));
  }

  @Test
  void getCarById() throws Exception {
    final var jsonString =
        objectMapper.writeValueAsString(
            objectMapper.readValue(
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
  void createCar() throws Exception {
    final var carRequest =
        CarCreateRequest.builder()
            .brandId(1L)
            .modelId(2L)
            .year(2019)
            .vin("4S3BTLB68B3286050")
            .mileage(20705)
            .build();

    final String result =
        objectMapper.writeValueAsString(
            objectMapper.readValue(
                new File("src/main/resources/tests/cars/afterAddCar.json"), JSONArray.class));

    mockMvc
        .perform(
            post("/api/v1/cars")
                .content(objectMapper.writeValueAsString(carRequest))
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mockMvc
        .perform(get("/api/v1/cars"))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(result));
  }

  @ParameterizedTest
  @MethodSource("provideCarRequestForException")
  void createCarIncorrectData(final CarCreateRequest carRequest, final String message)
      throws Exception {

    mockMvc
        .perform(
            post("/api/v1/cars")
                .content(objectMapper.writeValueAsString(carRequest))
                .header(CONTENT_TYPE, APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(),
                    instanceOf(MethodArgumentNotValidException.class)))
        .andExpect(
            result ->
                assertThat(result.getResolvedException().getMessage(), containsString(message)));
  }

  private static Stream<Arguments> provideCarRequestForException() {
    final var createRequest =
        CarCreateRequest.builder()
            .brandId(1L)
            .modelId(2L)
            .year(2019)
            .vin("4S3BILL68B328RGF0")
            .mileage(20705)
            .build();
    return Stream.of(
        Arguments.of(createRequest.toBuilder().brandId(null).build(), "Brand is mandatory"),
        Arguments.of(createRequest.toBuilder().modelId(null).build(), "Model is mandatory"),
        Arguments.of(
            createRequest.toBuilder().year(1899).build(),
            "Value should be greater then or equal to 1900"),
        Arguments.of(
            createRequest.toBuilder().year(2210).build(),
            "Value should be less then or equal to 2021"),
        Arguments.of(createRequest.toBuilder().year(null).build(), "Year is mandatory"),
        Arguments.of(createRequest.toBuilder().vin(null).build(), "VIN code is mandatory"),
        Arguments.of(
            createRequest.toBuilder().vin("4S3R0CK68").build(),
            "Value should be 17 characters long"),
        Arguments.of(
            createRequest.toBuilder().vin("4S3BILL68B328RGF0TR56").build(),
            "Value should be 17 characters long"),
        Arguments.of(createRequest.toBuilder().mileage(null).build(), "Mileage is mandatory"),
        Arguments.of(
            createRequest.toBuilder().mileage(-9000).build(),
            "Value should be greater then or equal to 0"));
  }

  @Test
  void updateCarPhoto() throws Exception {
    final var url = "/api/v1/cars/{car_id}/photos";
    final Integer carId = 1;
    final var methodType = PATCH.name();
    final var multipartFile =
        new MockMultipartFile(
            "photo", "sourceFile.jpeg", IMAGE_JPEG_VALUE, "Hello World".getBytes());

    mockMvc.perform(makeRequest(url, carId, methodType, multipartFile)).andExpect(status().isOk());
  }

  @Test
  void updateCarPhotoIncorrectMediaType() throws Exception {
    final var url = "/api/v1/cars/{car_id}/photos";
    final Integer carId = 1;
    final var methodType = PATCH.name();
    final var multipartFile =
        new MockMultipartFile(
            "photo", "sourceFile.tmp", MULTIPART_FORM_DATA_VALUE, "Hello World".getBytes());

    mockMvc
        .perform(makeRequest(url, carId, methodType, multipartFile))
        .andExpect(status().isUnsupportedMediaType());
  }

  private MockMultipartHttpServletRequestBuilder makeRequest(
      final String url, final Integer id, final String method, MockMultipartFile file) {
    final MockMultipartHttpServletRequestBuilder builder = multipart(url, id);
    builder.with(
        request -> {
          request.setMethod(method);
          return request;
        });
    return builder.file(file);
  }
}
