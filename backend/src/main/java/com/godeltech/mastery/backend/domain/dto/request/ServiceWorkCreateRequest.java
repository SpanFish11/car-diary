package com.godeltech.mastery.backend.domain.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ServiceWorkCreateRequest {

  @NotBlank(message = "Name of service work is mandatory")
  private String name;

  @DecimalMin(value = "0.00")
  @Digits(integer = 10, fraction = 2)
  private BigDecimal price;

  @NotNull private Boolean guarantee;
}
