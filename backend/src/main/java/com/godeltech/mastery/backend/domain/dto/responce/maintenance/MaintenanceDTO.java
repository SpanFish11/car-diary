package com.godeltech.mastery.backend.domain.dto.responce.maintenance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDTO {

  private Long id;

  private String operationNumber;

  private Integer mileage;

  private Integer timeInterval;

  private List<MaintenanceOperationDTO> operations;

  private List<DetailDTO> details;
}
