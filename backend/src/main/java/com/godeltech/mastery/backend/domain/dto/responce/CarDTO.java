package com.godeltech.mastery.backend.domain.dto.responce;

import java.math.BigDecimal;
import java.util.List;

public record CarDTO(
  Long id,
  BrandDTO brand,
  ModelDTO model,
  Integer year,
  String photoUrl,
  String vin,
  Integer mileage,
  Boolean ours,
  ClientDTO client,
  EquipmentDTO equipment,
  Boolean used,
  BigDecimal price,
  GuaranteeDTO guarantee,
  List<ServiceOperationRecordDTO> serviceOperations
) {}
