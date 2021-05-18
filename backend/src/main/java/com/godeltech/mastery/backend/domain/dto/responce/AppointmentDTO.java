package com.godeltech.mastery.backend.domain.dto.responce;

import com.godeltech.mastery.backend.domain.dto.responce.maintenance.MaintenanceDTO;
import com.godeltech.mastery.backend.domain.entity.AppointmentStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class AppointmentDTO {

  private Long id;
  private Boolean repairment;
  private MaintenanceDTO regularService;
  private CarDTO car;
  private String description;
  private LocalDate date;
  private AppointmentStatus status;
}
