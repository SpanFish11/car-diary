package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ChangePartCreateRequest {

  @NotNull(message = "Replaced is mandatory")
  private Boolean replaced;

  @NotBlank(message = "Model is mandatory")
  private String model;

  @NotNull(message = "Price is mandatory")
  @DecimalMin(value = "0.00")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal price;
}
