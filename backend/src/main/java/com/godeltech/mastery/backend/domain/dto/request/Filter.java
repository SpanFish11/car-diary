package com.godeltech.mastery.backend.domain.dto.request;

public record Filter(
  Long modelId,
  String vin,
  String lastname,
  Integer specificYear,
  Integer until,
  Integer from
) {}
