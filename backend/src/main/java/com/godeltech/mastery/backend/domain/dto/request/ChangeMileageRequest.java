package com.godeltech.mastery.backend.domain.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangeMileageRequest {

  @NotNull(message = "Car is mandatory")
  private Long carId;

  @NotNull(message = "Mileage is mandatory")
  @Min(value = 0, message = "Value should be greater then or equal to 0")
  @Max(value = 1000000, message = "Value should be less then or equal to 1000000")
  private Integer mileage;
}
