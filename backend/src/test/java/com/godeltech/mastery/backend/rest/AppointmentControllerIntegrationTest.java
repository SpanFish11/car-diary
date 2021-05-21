package com.godeltech.mastery.backend.rest;

import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.CONFIRM;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import java.io.File;
import java.util.stream.Stream;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"/tests/schema.sql",
    "/tests/rest/appointments/data.sql"}, executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = {"/tests/drop.sql"}, executionPhase = AFTER_TEST_METHOD)
class AppointmentControllerIntegrationTest {

  private static final String ALL_APPOINTMENTS =
      "src/test/resources/tests/rest/appointments/allAppointments.json";
  private static final String AFTER_ADD_APPOINTMENT =
      "src/test/resources/tests/rest/appointments/afterAddAppointments.json";
  private static final String AFTER_CHANGE_STATUS =
      "src/test/resources/tests/rest/appointments/afterChangeStatus.json";
  private static final String API_APPOINTMENTS = "/api/v1/appointments";
  private static final String API_APPOINTMENTS_BY_ID = "/api/v1/appointments/{appointment_id}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void getAllAppointments() throws Exception {
    final var appointments =
        objectMapper
            .writeValueAsString(
                objectMapper.readValue(new File(ALL_APPOINTMENTS), JSONArray.class));

    mockMvc
        .perform(get(API_APPOINTMENTS))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(appointments));
  }

  @WithUserDetails(value = "sopice5177@gridmire.com")
  @Test
  void createAppointment() throws Exception {
    final var appointment = new AppointmentCreateRequest(TRUE, 1L, "Description", 1L, now());
    final var json = objectMapper.writeValueAsString(appointment);
    final var appointments =
        objectMapper
            .writeValueAsString(
                objectMapper.readValue(new File(AFTER_ADD_APPOINTMENT), JSONArray.class));

    mockMvc
        .perform(post(API_APPOINTMENTS).contentType(APPLICATION_JSON).content(json))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isCreated()).andExpect(content().json("2"));

    mockMvc
        .perform(get(API_APPOINTMENTS))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(appointments));
  }

  @ParameterizedTest
  @MethodSource("provideAppointmentRequestForException")
  void createAppointmentIncorrectData(final AppointmentCreateRequest request,
      final String message) throws Exception {

    mockMvc
        .perform(post(API_APPOINTMENTS).contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().is4xxClientError())
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

  @Test
  void changeStatus() throws Exception {
    final var appointmentId = 1;
    final var status = CONFIRM.name();
    final var appointment =
        objectMapper
            .writeValueAsString(
                objectMapper.readValue(new File(AFTER_CHANGE_STATUS), JSONObject.class));

    mockMvc.perform(put(API_APPOINTMENTS_BY_ID, appointmentId)
        .param("status", status))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(appointment));
  }

  // TODO send status in body

  private static Stream<Arguments> provideAppointmentRequestForException() {
    return Stream.of(Arguments.of(new AppointmentCreateRequest(null, 1L, "Description", 1L, now()),
        "repairment is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 0L, "Description", 1L, now()),
            "Value should be greater then or equal to 1"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, null, 1L, now()),
            "description is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", null, now()),
            "car is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", 0L, now()),
            "Value should be greater then or equal to 1"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", 1L, null),
            "Appointment date is mandatory"),
        Arguments.of(new AppointmentCreateRequest(TRUE, 1L, "Description", 1L, now().minusDays(1)),
            "Appointment date should be in the present of future")
    );
  }
}