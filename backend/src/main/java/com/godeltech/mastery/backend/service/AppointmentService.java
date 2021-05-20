package com.godeltech.mastery.backend.service;

import com.godeltech.mastery.backend.domain.dto.request.AppointmentCreateRequest;
import com.godeltech.mastery.backend.domain.dto.responce.AppointmentDTO;
import com.godeltech.mastery.backend.domain.entity.AppointmentStatus;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface AppointmentService {

  Long createAppointment(AppointmentCreateRequest createRequest, Authentication principal);

  List<AppointmentDTO> getAllAppointments();

  AppointmentDTO changeAppointmentStatus(Long appointmentId, AppointmentStatus status);

  List<AppointmentDTO> getAllAppointments(Authentication principal);
}
