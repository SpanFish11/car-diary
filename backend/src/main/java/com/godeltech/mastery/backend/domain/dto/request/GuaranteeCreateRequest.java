package com.godeltech.mastery.backend.domain.dto.request;

import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuaranteeCreateRequest {

  @NotNull(message = "Start date is mandatory")
  @PastOrPresent(message = "Start date should be in the present or past")
  @Temporal(DATE)
  private LocalDate start;

  @NotNull(message = "Extended is mandatory")
  private Boolean extended;
}
