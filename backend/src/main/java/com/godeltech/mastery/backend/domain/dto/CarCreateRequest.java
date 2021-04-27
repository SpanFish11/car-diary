package com.godeltech.mastery.backend.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CarCreateRequest {

  @NotNull(message = "Brand is mandatory")
  @Min(value = 1, message = "Value should be greater then or equal to 1")
  private Long brandId;

  @NotNull(message = "Model is mandatory")
  @Min(value = 1, message = "Value should be greater then or equal to 1")
  private Long modelId;

  @NotNull(message = "Year is mandatory")
  @Min(value = 1900, message = "Value should be greater then or equal to 1900")
  @Max(value = 2021, message = "Value should be less then or equal to 2021")
  private Integer year;

  @NotBlank(message = "VIN code is mandatory")
  @Size(min = 17, max = 17, message = "Value should be 17 characters long")
  private String vin;

  @NotNull(message = "Mileage is mandatory")
  @Min(value = 0, message = "Value should be greater then or equal to 0")
  private Integer mileage;
}
