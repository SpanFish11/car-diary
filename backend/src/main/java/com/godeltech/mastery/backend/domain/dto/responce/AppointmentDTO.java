package com.godeltech.mastery.backend.domain.dto.responce;

import com.godeltech.mastery.backend.domain.entity.AppointmentStatus;
import java.time.LocalDate;

public record AppointmentDTO(Long id, Boolean repairment, MaintenanceDTO regularService, CarDTO car, String description, LocalDate date, AppointmentStatus status) {}
