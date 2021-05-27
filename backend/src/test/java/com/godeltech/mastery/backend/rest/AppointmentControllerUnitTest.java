package com.godeltech.mastery.backend.rest;

import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.CONFIRM;
import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.DENIED;
import static java.time.LocalDate.now;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.entity.AppointmentStatus;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.service.AppointmentService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AppointmentControllerUnitTest {

  @Mock private AppointmentService appointmentService;
  @InjectMocks private AppointmentController appointmentController;

  @Test
  void getAllAppointments() {
    final var date = now().plusDays(34);
    final var appointments =
        List.of(
            new AppointmentDTO(1L, true, null, null, "Something break", date, DENIED),
            new AppointmentDTO(2L, true, null, null, "Check oil", date, CONFIRM));
    final ResponseEntity<List<AppointmentDTO>> expected = ok(appointments);

    given(appointmentService.getAllAppointments()).willReturn(appointments);

    final var actual = appointmentController.getAllAppointments();
    assertThat(actual, is(expected));

    then(appointmentService).should(only()).getAllAppointments();
  }

  @Test
  @WithMockUser(value = "userexample@app.com")
  void createAppointment() {
    final User auth = (User) getContext().getAuthentication().getPrincipal();
    final var createRequest = new AppointmentCreateRequest(false, null, "Not working", 3L, now());
    final var appointmentId = 4L;
    final ResponseEntity<Long> expected = new ResponseEntity<>(appointmentId, CREATED);

    given(appointmentService.createAppointment(createRequest, auth)).willReturn(appointmentId);

    final var actual = appointmentController.createAppointment(createRequest, auth);
    assertThat(actual, is(expected));

    then(appointmentService).should(only()).createAppointment(refEq(createRequest), refEq(auth));
  }

  @Test
  @WithMockUser(value = "user@app.com")
  void createAppointmentReturnException() {
    final User auth = (User) getContext().getAuthentication().getPrincipal();
    final var maintenanceId = 67L;
    final var createRequest =
        new AppointmentCreateRequest(false, maintenanceId, "Not working", 3L, now());
    final var exception = new EntityNotFoundException("maintenance", maintenanceId);

    given(appointmentService.createAppointment(createRequest, auth)).willThrow(exception);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> appointmentController.createAppointment(createRequest, auth));
    assertThat(actual.getMessage(), is(exception.getMessage()));

    then(appointmentService).should(only()).createAppointment(refEq(createRequest), refEq(auth));
  }

  @Test
  void changeStatus() {
    final var appointmentId = 4L;
    final AppointmentStatus status = CONFIRM;
    final var appointmentDto =
        new AppointmentDTO(appointmentId, true, null, null, "Something break", now(), status);
    final ResponseEntity<AppointmentDTO> expected = ok(appointmentDto);

    given(appointmentService.changeAppointmentStatus(appointmentId, status))
        .willReturn(appointmentDto);

    final var actual = appointmentController.changeStatus(appointmentId, status);
    assertThat(actual, is(expected));

    then(appointmentService).should(only()).changeAppointmentStatus(eq(appointmentId), eq(status));
  }

  @Test
  void changeStatusWithIncorrectId() {
    final var incorrectId = 2131L;
    final AppointmentStatus status = DENIED;
    final var exception = new EntityNotFoundException("appointment", incorrectId);

    given(appointmentService.changeAppointmentStatus(incorrectId, status)).willThrow(exception);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> appointmentController.changeStatus(incorrectId, status));
    assertThat(actual.getMessage(), is(exception.getMessage()));

    then(appointmentService).should(only()).changeAppointmentStatus(eq(incorrectId), eq(status));
  }
}
