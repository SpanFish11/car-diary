package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class CarCreateManagerRequest extends CarCreateRequest {

  @NotNull(message = "Client is mandatory")
  @Min(value = 1, message = "Value should be greater then or equal to 1")
  private Long clientId;

  @NotNull(message = "Client is mandatory")
  private Boolean ours;

  @NotNull(message = "Used is mandatory")
  private Boolean used;
}
