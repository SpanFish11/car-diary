package com.godeltech.mastery.backend.domain.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ChangePartCreateRequest {

  private Boolean replaced;
  private String model;
  private BigDecimal price;
}
