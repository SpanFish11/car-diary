package com.godeltech.mastery.backend.domain.dto.request;

import static javax.persistence.TemporalType.DATE;

import java.time.LocalDate;
import javax.persistence.Temporal;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentCreateRequest {

  @NotNull(message = "repairment is mandatory")
  private Boolean repairment;

  @Positive(message = "Value should be greater then or equal to 1")
  private Long maintainceId;

  @NotNull(message = "description is mandatory")
  private String description;

  @NotNull(message = "car is mandatory")
  @Min(value = 1, message = "Value should be greater then or equal to 1")
  private Long carId;

  @NotNull(message = "Appointment date is mandatory")
  @FutureOrPresent(message = "Appointment date should be in the present of future")
  @Temporal(DATE)
  private LocalDate date;
}
