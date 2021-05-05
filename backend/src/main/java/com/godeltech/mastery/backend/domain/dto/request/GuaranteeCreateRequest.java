package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

import static javax.persistence.TemporalType.DATE;

@Data
public class GuaranteeCreateRequest {

  @NotNull(message = "Start date is mandatory")
  @PastOrPresent(message = "Start date should be in the present or past")
  @Temporal(DATE)
  private LocalDate start;

  @NotNull(message = "Extended is mandatory")
  private Boolean extended;
}
