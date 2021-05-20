package com.godeltech.mastery.backend.domain.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

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
