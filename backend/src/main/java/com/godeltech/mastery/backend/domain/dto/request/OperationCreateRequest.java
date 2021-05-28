package com.godeltech.mastery.backend.domain.dto.request;

import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Temporal;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class OperationCreateRequest {

  private String serviceOperationNumber;

  @NotNull(message = "Date is mandatory")
  @PastOrPresent(message = "Date should be in the present or past")
  @Temporal(DATE)
  private LocalDate date;

  @NotNull(message = "Mileage is mandatory")
  @Min(value = 0, message = "Value should be greater then or equal to 0")
  private Integer mileage;

  @NotNull(message = "Service works are mandatory")
  @Size(min = 1, message = "Value should be have at least one service work")
  private List<@Valid ServiceWorkCreateRequest> serviceWorks;

  private List<@Valid ChangePartCreateRequest> changableParts;
}
