package com.godeltech.mastery.backend.rest;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.godeltech.mastery.backend.arguments.CarCreateRequestArgumentsProvider;
import com.godeltech.mastery.backend.arguments.GetAllCarsPageSizeAndFilterArgumentsProvider;
import com.godeltech.mastery.backend.domain.dto.request.CarCreateManagerRequest;
import com.godeltech.mastery.backend.domain.dto.request.Filter;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.util.TestUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/tests/schema.sql",
    "/tests/rest/cars/data.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/tests/drop.sql"}, executionPhase = AFTER_TEST_METHOD)
class CarControllerIntegrationTest {

  private static final String CAR_CREATE_REQUEST = "carCreateRequest.json";
  private static final String CREATED_CAR = "src/test/resources/tests/rest/cars/afterAddCar.json";
  private static final String CAR_BY_ID = "src/test/resources/tests/rest/cars/findCarById.json";
  private static final String API_CARS = "/api/v1/cars";
  private static final String GET_BY_ID = API_CARS + "/{car_id}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TestUtils testUtils;

  @BeforeEach
  void setUp() {
    testUtils.setPath("classpath:/tests/rest/cars/");
  }

  @ParameterizedTest
  @ArgumentsSource(GetAllCarsPageSizeAndFilterArgumentsProvider.class)
  void getAllCars(final String fileName, final Integer page, final Integer size,
      final Filter filter) throws Exception {
    final var params = testUtils.toParams(page, size, filter);
    final var json = testUtils.readFileToString(fileName);

    mockMvc
        .perform(get(API_CARS).params(params))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(json));
  }

  @Test
  void getAllCars_given_negativePageIndex_should_isBadRequest() throws Exception {
    final var page = -1;
    mockMvc
        .perform(get(API_CARS).param("page", valueOf(page)))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(IllegalArgumentException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Page index must not be less than zero!")));
  }

  @Test
  void getAllCars_given_negativePageSizeIndex_should_isBadRequest() throws Exception {
    final var size = -1;
    mockMvc
        .perform(get(API_CARS).param("size", valueOf(size)))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(IllegalArgumentException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Page size must not be less than one!")));
  }

  @Test
  void getCarById() throws Exception {
    final var carId = 1;
    final var jsonString = testUtils.toJSONObject(CAR_BY_ID);

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
  void createCar() throws Exception {
    final var newCar = CarCreateManagerRequest
        .managerRequestBuilder()
        .modelId(2L)
        .equipmentId(1L)
        .mileage(0)
        .ours(TRUE)
        .clientId(2L)
        .price(new BigDecimal(50000))
        .year(1950)
        .used(FALSE)
        .vin("4S3BMHB68B3290050")
        .build();
    final var request = testUtils.objectToJSON(newCar);
    final var responseAfterCreate = "7";
    final var response = testUtils.toJSONObject(CREATED_CAR);

    mockMvc.perform(post(API_CARS).contentType(APPLICATION_JSON).content(request))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().json(responseAfterCreate));

    mockMvc
        .perform(get(GET_BY_ID, responseAfterCreate))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(response));
  }

  @ParameterizedTest
  @ArgumentsSource(CarCreateRequestArgumentsProvider.class)
  void createCarIncorrectData(final String message, final String... carRequest) throws Exception {
    mockMvc
        .perform(post(API_CARS).contentType(APPLICATION_JSON)
            .content(testUtils.replaceAllTokens(CAR_CREATE_REQUEST, carRequest)))
        .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(),
                    instanceOf(MethodArgumentNotValidException.class)))
        .andExpect(
            result ->
                assertThat(requireNonNull(result.getResolvedException()).getMessage(),
                    containsString(message)));
  }

  // TODO EntityNotFoundException

  @Test
  void updateCarPhoto() {
    // TODO
  }
}