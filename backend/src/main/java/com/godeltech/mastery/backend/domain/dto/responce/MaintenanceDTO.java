package com.godeltech.mastery.backend.domain.dto.responce;

import java.util.List;

public record MaintenanceDTO(
  Long id,
  String operationNumber,
  Integer mileage,
  Integer timeInterval,
  List<MaintenanceOperationDTO> operations,
  List<DetailDTO> details
) {}
