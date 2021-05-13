package com.godeltech.mastery.backend.domain.dto.responce.maintenance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDTO {

  private Long id;
  private String name;
  private BigDecimal price;
}