package com.godeltech.mastery.backend.service.impl;

import static com.godeltech.mastery.backend.domain.entity.AppointmentStatus.PENDING;
import static java.lang.Boolean.TRUE;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.entity.Appointment;
import com.godeltech.mastery.backend.domain.entity.AppointmentStatus;
import com.godeltech.mastery.backend.domain.entity.Car;
import com.godeltech.mastery.backend.domain.entity.Client;
import com.godeltech.mastery.backend.domain.entity.Maintenance;
import com.godeltech.mastery.backend.exception.EntityNotFoundException;
import com.godeltech.mastery.backend.mapper.AppointmentMapper;
import com.godeltech.mastery.backend.repository.AppointmentRepository;
import com.godeltech.mastery.backend.repository.MaintenanceRepository;
import com.godeltech.mastery.backend.service.AppointmentService;
import com.godeltech.mastery.backend.service.CarService;
import com.godeltech.mastery.backend.service.ClientService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

  private final AppointmentRepository appointmentRepository;
  private final AppointmentMapper appointmentMapper;
  private final MaintenanceRepository maintenanceRepository;
  private final CarService carService;
  private final ClientService clientService;

  @Override
  public Long createAppointment(
      final AppointmentCreateRequest createRequest, final Authentication principal) {
    final var appointment = appointmentMapper.toEntity(createRequest);
    setRegularService(appointment, createRequest);
    final Long carId = createRequest.getCarId();
    final var client = clientService.getClient(principal);
    setCarByCurrentClient(appointment, carId, client);
    appointment.setStatus(PENDING);
    return appointmentRepository.save(appointment).getId();
  }

  @Override
  public List<AppointmentDTO> getAllAppointments() {
    return appointmentMapper.toListDTO(appointmentRepository.findAll());
  }

  @Override
  public AppointmentDTO changeAppointmentStatus(
      final Long appointmentId, final AppointmentStatus status) {
    final var appointment = getAppointmentById(appointmentId);
    appointment.setStatus(status);
    return appointmentMapper.toDTO(appointmentRepository.save(appointment));
  }

  @Override
  public List<AppointmentDTO> getAllAppointments(Authentication principal) {
    final List<AppointmentDTO> appointments = new ArrayList<>();
    final Set<Car> cars = clientService.getClient(principal).getCars();
    cars.forEach(car -> appointments.addAll(appointmentMapper.toListDTO(car.getAppointments())));
    return appointments;
  }

  private Appointment getAppointmentById(final Long id) {
    return appointmentRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("appointment", id));
  }

  private Maintenance getMaintenanceById(final Long id) {
    return maintenanceRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("maintenance", id));
  }

  private void setRegularService(Appointment appointment, AppointmentCreateRequest createRequest) {
    if (TRUE.equals(appointment.getRepairment())) {
      appointment.setRegularService(null);
    } else {
      final var maintenance = getMaintenanceById(createRequest.getMaintainceId());
      appointment.setRegularService(maintenance);
    }
  }

  private void setCarByCurrentClient(Appointment appointment, Long carId, Client client) {
    final var car = carService.findCarById(carId);
    final boolean exists =
        client.getCars().stream().anyMatch(currentCar -> currentCar.getId().equals(carId));
    if (exists) {
      appointment.setCar(car);
    } else {
      throw new IllegalArgumentException("Client does not have car with id = " + carId);
    }
  }
}
