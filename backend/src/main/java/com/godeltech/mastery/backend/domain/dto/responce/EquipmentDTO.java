package com.godeltech.mastery.backend.domain.dto.responce;

public record EquipmentDTO(
  Long id,
  String name,
  String engineType,
  String transmissionType,
  Double engineSize,
  Integer horsePower
) {}
