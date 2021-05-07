package com.godeltech.mastery.backend.domain.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceWorkDTO {

  private Long id;

  private String name;

  private BigDecimal price;

  private Boolean guarantee;
}
