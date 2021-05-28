package com.godeltech.mastery.backend.domain.dto.request;

import java.math.BigDecimal;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CarCreateManagerRequest extends CarCreateRequest {

  @NotNull(message = "Client is mandatory")
  @Min(value = 1, message = "Value should be greater then or equal to 1")
  private Long clientId;

  @NotNull(message = "Ours is mandatory")
  private Boolean ours;

  @NotNull(message = "Used is mandatory")
  private Boolean used;

  @Builder(builderMethodName = "managerRequestBuilder")
  public CarCreateManagerRequest(
      Long modelId,
      Integer year,
      String vin,
      Integer mileage,
      BigDecimal price,
      Long equipmentId,
      Long clientId,
      Boolean ours,
      Boolean used) {
    super(modelId, year, vin, mileage, price, equipmentId);
    this.clientId = clientId;
    this.ours = ours;
    this.used = used;
  }
}
