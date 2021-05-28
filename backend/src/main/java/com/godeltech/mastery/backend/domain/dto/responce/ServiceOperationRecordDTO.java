package com.godeltech.mastery.backend.domain.dto.responce;

import java.time.LocalDate;
import java.util.List;

public record ServiceOperationRecordDTO(
  Long id,
  String serviceOperationNumber,
  LocalDate date,
  Integer mileage,
  List<ServiceWorkDTO> serviceWorks,
  List<ChangeablePartDTO> changableParts
) {}
