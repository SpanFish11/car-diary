package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

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
  private BigDecimal price;
}
