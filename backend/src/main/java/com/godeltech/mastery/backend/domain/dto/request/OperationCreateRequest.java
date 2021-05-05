package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
public class OperationCreateRequest {

  private String serviceOperationNumber;

  @NotNull(message = "Date is mandatory")
  @PastOrPresent(message = "Date should be ")
  private LocalDate date;

  @NotNull(message = "Mileage is mandatory")
  @Min(value = 0, message = "Value should be greater then or equal to 0")
  private Integer mileage;

  @NotNull(message = "Service works are mandatory")
  @Size(min = 1, message = "Value should be have at least one service work")
  private List<ServiceWorkCreateRequest> serviceWorks;

  private List<ChangePartCreateRequest> changableParts;
}
