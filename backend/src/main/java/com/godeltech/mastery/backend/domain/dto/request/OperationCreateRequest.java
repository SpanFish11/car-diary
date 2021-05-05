package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OperationCreateRequest {

  private String serviceOperationNumber;
  private LocalDate date;
  private Integer mileage;
  private List<ServiceWorkCreateRequest> serviceWorks;
  private List<ChangePartCreateRequest> changableParts;
}
