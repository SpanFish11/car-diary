package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangablePartDTO {

  private Long id;

  private Boolean replaced;

  private String model;

  private BigDecimal price;
}
