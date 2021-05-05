package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ServiceWorkCreateRequest {

  @NotBlank(message = "Name of service work is mandatory")
  private String name;

  private BigDecimal price;

  @NotNull
  private Boolean guarantee;
}
