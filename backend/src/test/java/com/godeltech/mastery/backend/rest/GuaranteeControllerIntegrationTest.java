package com.godeltech.mastery.backend.rest;

import static java.lang.Boolean.FALSE;
import static java.time.LocalDate.now;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.domain.dto.request.GuaranteeCreateRequest;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.utils.TemplateDoc;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
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
    "/tests/rest/guarantee/data.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/tests/drop.sql"}, executionPhase = AFTER_TEST_METHOD)
class GuaranteeControllerIntegrationTest {

  private static final String GUARANTEE_BY_ID = "getGuarantee.json";
  private static final String GUARANTEE_AFTER_EXTEND = "afterExtendGuarantee.json";
  private static final String AFTER_ADD_GUARANTEE = "afterAddGuarantee.json";
  private static final String API_GUARANTEE = "/api/v1/guarantee";
  private static final String API_GET_GUARANTEE = API_GUARANTEE + "/{car_id}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private TemplateDoc templateDoc;

  @BeforeEach
  void setUp() {
    templateDoc.setPath("classpath:/tests/rest/guarantee/");
  }

  @Test
  void createGuarantee() throws Exception {
    final var carId = 2;
    final var request = objectMapper.writeValueAsString(new GuaranteeCreateRequest(now(), FALSE));
    final var responseAfterCreate = "3";
    final var response = templateDoc
        .replaceAllTokens(AFTER_ADD_GUARANTEE, "start", now().toString(), "stop",
            now().plusYears(3).toString());

    mockMvc.perform(post(API_GET_GUARANTEE, carId).contentType(APPLICATION_JSON).content(request))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().json(responseAfterCreate));

    mockMvc
        .perform(get(API_GET_GUARANTEE, carId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(response));
  }

  @Test
  void createGuarantee_given_carId_should_status_isBadRequest() throws Exception {
    final var carId = 1;
    final var request = objectMapper.writeValueAsString(new GuaranteeCreateRequest(now(), FALSE));

    mockMvc
        .perform(post(API_GET_GUARANTEE, carId).contentType(APPLICATION_JSON).content(request))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(IllegalArgumentException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Guarantee already exist")));
  }

  @Test
  void getGuarantee() throws Exception {
    final var carId = 1;
    final var guarantee = templateDoc.readFileToString(GUARANTEE_BY_ID);

    mockMvc.perform(get(API_GET_GUARANTEE, carId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(guarantee));
  }

  @Test
  void getGuarantee_given_carId_should_status_isNotFound() throws Exception {
    final var carId = 6;

    mockMvc
        .perform(get(API_GET_GUARANTEE, carId))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(EntityNotFoundException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Could not find any car with the ID 6.")));
  }

  @Test
  void extendedGuarantee() throws Exception {
    final var carId = 1;
    final var guarantee = templateDoc.readFileToString(GUARANTEE_AFTER_EXTEND);

    mockMvc.perform(put(API_GET_GUARANTEE, carId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(guarantee));
  }

  @Test
  void extendedGuarantee_given_carId_should_status_isNotFound() throws Exception {
    final var carId = 6;

    mockMvc
        .perform(put(API_GET_GUARANTEE, carId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(), instanceOf(EntityNotFoundException.class)))
        .andExpect(
            result ->
                assertThat(
                    requireNonNull(result.getResolvedException()).getMessage(),
                    is("Could not find any car with the ID 6.")));
  }

  @ParameterizedTest
  @MethodSource("provideExtendedGuaranteeRequestForException")
  void extendedGuarantee_given_carId_should_status_isBadRequest(final Integer carId,
      final String message)
      throws Exception {
    mockMvc
        .perform(put(API_GET_GUARANTEE, carId))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertThat(
                    result.getResolvedException(),
                    instanceOf(IllegalArgumentException.class)))
        .andExpect(
            result ->
                assertThat(requireNonNull(result.getResolvedException()).getMessage(),
                    containsString(message)));
  }

  private static Stream<Arguments> provideExtendedGuaranteeRequestForException() {
    return Stream.of(Arguments.of(2, "Guarantee doesn't exist"),
        Arguments.of(3, "Guarantee already extended"));
  }
}