package com.godeltech.mastery.backend.service.impl;

import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.CONFIRM;
import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.DENIED;
import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.PENDING;
import static java.lang.String.format;
import static java.time.LocalDate.now;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.entity.Appointment;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.entity.Maintenance;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.AppointmentMapper;
import com.godeltech.mastery.backend.repository.AppointmentRepository;
import com.godeltech.mastery.backend.repository.MaintenanceRepository;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.ClientService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AppointmentServiceUnitTest {

  @Mock AppointmentRepository appointmentRepository;
  @Mock AppointmentMapper appointmentMapper;
  @Mock MaintenanceRepository maintenanceRepository;
  @Mock CarService carService;
  @Mock ClientService clientService;

  @InjectMocks AppointmentServiceImpl appointmentService;

  @Test
  @WithMockUser(value = "userexample@app.com")
  void createAppointment() {
    final User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final var createRequest = new AppointmentCreateRequest(false, 1L, "Not working", 3L, now());
    final var maintenance = new Maintenance();
    maintenance.setId(createRequest.getMaintainceId());
    maintenance.setOperationNumber("Extra TO-2");
    final var car = Car.builder().id(createRequest.getCarId()).vin("QAZXSWEDCVFRTGBHJ").build();
    final var client = Client.builder().email(auth.getUsername()).cars(Set.of(car)).build();
    final var appointment =
        Appointment.builder()
            .repairment(false)
            .regularService(maintenance)
            .car(car)
            .description("Something wrong")
            .date(now())
            .status(PENDING)
            .build();
    final var expected = appointment.toBuilder().id(5L).build();

    given(appointmentMapper.toEntity(createRequest)).willReturn(appointment);
    given(maintenanceRepository.findById(createRequest.getMaintainceId()))
        .willReturn(Optional.of(maintenance));
    given(clientService.getClient(auth)).willReturn(client);
    given(carService.findCarById(createRequest.getCarId())).willReturn(car);
    given(appointmentRepository.save(appointment)).willReturn(expected);

    final var actual = appointmentService.createAppointment(createRequest, auth);
    assertThat(actual, is(expected.getId()));

    then(appointmentMapper).should(only()).toEntity(createRequest);
    then(maintenanceRepository).should(only()).findById(createRequest.getMaintainceId());
    then(clientService).should(only()).getClient(auth);
    then(carService).should(only()).findCarById(createRequest.getCarId());
    then(appointmentRepository).should(only()).save(appointment);
  }

  @Test
  @WithMockUser(value = "userexample@app.com")
  void createAppointmentWithRepairment() {
    final User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final var createRequest = new AppointmentCreateRequest(true, 1L, "Not working", 3L, now());
    final var car = Car.builder().id(createRequest.getCarId()).vin("QAZXSWEDCVFRTGBHJ").build();
    final var client = Client.builder().email(auth.getUsername()).cars(Set.of(car)).build();
    final var appointment =
        Appointment.builder()
            .repairment(true)
            .car(car)
            .description("Something wrong")
            .date(now())
            .status(PENDING)
            .build();
    final var expected = appointment.toBuilder().id(5L).build();

    given(appointmentMapper.toEntity(createRequest)).willReturn(appointment);
    given(clientService.getClient(auth)).willReturn(client);
    given(carService.findCarById(createRequest.getCarId())).willReturn(car);
    given(appointmentRepository.save(appointment)).willReturn(expected);

    final var actual = appointmentService.createAppointment(createRequest, auth);
    assertThat(actual, is(expected.getId()));

    then(appointmentMapper).should(only()).toEntity(createRequest);
    then(maintenanceRepository).should(never()).findById(createRequest.getMaintainceId());
    then(clientService).should(only()).getClient(auth);
    then(carService).should(only()).findCarById(createRequest.getCarId());
    then(appointmentRepository).should(only()).save(appointment);
  }

  @Test
  @WithMockUser(value = "userexample@app.com")
  void createAppointmentWithIncorrectMaintenanceId() {
    final User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final var createRequest = new AppointmentCreateRequest(false, 1L, "Not working", 3L, now());
    final var appointment =
        Appointment.builder().repairment(false).description("Something wrong").date(now()).build();
    final var message =
        format("Could not found maintenance with id = %d", createRequest.getMaintainceId());
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(appointmentMapper.toEntity(createRequest)).willReturn(appointment);
    given(maintenanceRepository.findById(createRequest.getMaintainceId())).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> appointmentService.createAppointment(createRequest, auth));
    assertThat(actual.getMessage(), is(message));

    then(appointmentMapper).should(only()).toEntity(createRequest);
    then(maintenanceRepository).should(only()).findById(createRequest.getMaintainceId());
  }

  @Test
  void getAllAppointmentsManager() {
    final List<Appointment> appointments =
        List.of(new Appointment(1L, false, null, null, "Something wrong", now(), PENDING));

    final List<AppointmentDTO> expected =
        List.of(new AppointmentDTO(1L, false, null, null, "Something wrong", now(), PENDING));

    given(appointmentRepository.findAll()).willReturn(appointments);
    given(appointmentMapper.toListDTO(appointments)).willReturn(expected);

    final var actual = appointmentService.getAllAppointments();
    assertThat(actual, is(expected));

    then(appointmentRepository).should(only()).findAll();
    then(appointmentMapper).should(only()).toListDTO(appointments);
  }

  @Test
  void changeAppointmentStatus() {
    final Long id = 3L;
    final var appointment =
        new Appointment(id, false, null, null, "Something wrong", now(), PENDING);
    final var expected =
        new AppointmentDTO(id, false, null, null, "Something wrong", now(), DENIED);

    given(appointmentRepository.findById(id)).willReturn(Optional.of(appointment));
    given(appointmentRepository.save(appointment)).willReturn(appointment);
    given(appointmentMapper.toDTO(appointment)).willReturn(expected);

    final var actual = appointmentService.changeAppointmentStatus(id, DENIED);
    assertThat(actual, is(expected));

    then(appointmentRepository).should(times(1)).findById(id);
    then(appointmentRepository).should(times(1)).save(appointment);
    then(appointmentMapper).should(only()).toDTO(appointment);
  }

  @Test
  void changeAppointmentStatusWithIncorrectId() {
    final Long id = 123L;
    final var message = format("Could not found appointment with id = %d", id);
    final EntityNotFoundException excepted = new EntityNotFoundException(message);

    given(appointmentRepository.findById(id)).willThrow(excepted);

    final var actual =
        assertThrows(
            EntityNotFoundException.class,
            () -> appointmentService.changeAppointmentStatus(id, DENIED));
    assertThat(actual.getMessage(), is(message));

    then(appointmentRepository).should(only()).findById(id);
  }

  @Test
  @WithMockUser(value = "sopice5177@gridmire.com")
  void getAllAppointmentsClient() {
    final User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final List<Appointment> appointments =
        List.of(new Appointment(2L, false, null, null, "Something wrong", now(), CONFIRM));
    final var car =
        Car.builder().id(3L).vin("DFCL69F108UIIU7MWL").appointments(appointments).build();
    final var client =
        Client.builder()
            .id(34L)
            .firstName("John")
            .lastName("Snow")
            .email(auth.getUsername())
            .cars(Set.of(car))
            .build();
    final List<AppointmentDTO> expected =
        List.of(new AppointmentDTO(2L, false, null, null, "Something wrong", now(), CONFIRM));
    given(clientService.getClient(auth)).willReturn(client);
    given(appointmentMapper.toListDTO(appointments)).willReturn(expected);

    final var actual = appointmentService.getAllAppointments(auth);
    assertThat(actual, is(expected));

    then(clientService).should(only()).getClient(auth);
    then(appointmentMapper).should(only()).toListDTO(appointments);
  }
}
