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

import com.godeltech.mastery.backend.arguments.AppointmentRequestForExceptionArgumentsProvider;
import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
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
  private TestUtils testUtils;

  @BeforeEach
  void setUp() {
    testUtils.setPath("classpath:/tests/rest/appointments/");
  }

  @Test
  void getAllAppointments() throws Exception {
    final var appointments = testUtils.toJSONArray(ALL_APPOINTMENTS);

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
    final var json = testUtils.objectToJSON(appointment);
    final var appointments = testUtils
        .replaceAllTokens("afterAddAppointments.json", "date", now().toString());

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
  @ArgumentsSource(AppointmentRequestForExceptionArgumentsProvider.class)
  void createAppointmentIncorrectData(final AppointmentCreateRequest request,
      final String message) throws Exception {

    mockMvc
        .perform(post(API_APPOINTMENTS).contentType(APPLICATION_JSON)
            .content(testUtils.objectToJSON(request)))
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
    final var appointment = testUtils.toJSONObject(AFTER_CHANGE_STATUS);

    mockMvc.perform(put(API_APPOINTMENTS_BY_ID, appointmentId)
        .param("status", status))
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(appointment));
  }

  // TODO send status in body
}